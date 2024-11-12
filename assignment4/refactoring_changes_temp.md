# Major Refactoring operation

## Reasons for consideration

- The view (GUI) needs to be able to communicate with the controller about which command has been
  invoked by the user. Along with this, the view also needs to communicate the parameters.
- This refactor was *considered unnecessary* until now because the command and the arguments were
  passed as `String` objects to the model when the application solely depended on the CLI for user
  input.

> The current design can still support the addition of a GUI. But, it involves construction of the
> command string and passing it to the model. Then the model again deconstructs the string into
> arguments. This is an inefficient method because of an unncessary layer of depending on strings.

- From a design perspective, the current design fails to seperate the actions of the model and
  controller effectively. The controller essentially passes everything to the model and expects it
  to take care of all the validation. However, the role of the `controller` is to orchestrate the
  validation and calling the appropriate method on the model. The role of the `model` is to *take
  care* of the business logic alone and not concern about the user input.

## Key Objectives

- The controller should handle the user input and delegate to the model by calling on the
  appropriate command and passing the arguments.
- The view must be able to clearly define without overhead, the command that has been invoked by the
  user and pass the arguments without overhead.

> Overhead here refers to avoiding tedious steps such as converting back and forth from strings.

- While we are aware that pulling the Command Factory out of the model into the controller will
  increase coupling, we intend to keep it as minimal as possible to ensure the extension of the
  model.
- When extending the model, the only changes that are required must involve a static registration of
  the new command, the command class itself, and its invoking UI component in the view. The
  controller must ideally remain unchanged.

## Known Pitfalls

- This refactor will **increase the coupling** between the model and controller as it may require
  editing the controller when a new command is added.

> Until now, extension of the model to add a new command was straightforward and did not involve
> making changes to the controller package.
> To add a new command:
> - Create the command class in the `model.command` package.
> - Add a new entry to the `command.CommandEnum`class in the model.
>
> As you can notice, at no point is the `controller` package modified.

Hence we need to ensure that this refactor results in little to no changes to the controller when
the model is extended.

## Highlights of the refactor

- Instead of having to rely on a static implementation of an enumeration, we will register the
  commands into the controller at runtime. When the model is instantiated and passed to the
  controller, the controller will request the model for details on all the commands and register
  them on a `Map`.

```java
// in app.App.main()
Controller controller = new Controller(in, out);
controller.run(new Model(), new View());

// in controller.Controller.run():
    this.commandMap = model.registerCommands(); // expects a HashMap of the commands from the model.
```
- We are introducing the `arguments` package to handle the command arguments.
    - An argument interface.
        - Concrete implementations of the argument interface are different type of supported
          arguments. We require `int`, `String` and `File` types for our arguments. Hence, we add
          the 3 concrete implementations of the `Argument` interface.
        - An `ArgumentWrapper` class consists of an internal `Map` to map the argument (ordering)
          number to the `Argument` value.
```java
// in the view
set(1,new IntArgument(2)); // set first argument as an integer

set(2,new StringArgument("koala")); // set second argument as a string.

set(3,new FileArgument("res/image/koala.png")); // set third argument as a file.

// in the controller:
get(1) // to get the first argument

get(2) // to get the second argument
// and so on.
```

- The view will maintain a list of commands that it is using. During runtime, the view will register
  with the controller, all the commands it intends to use. If the controller is unable to map a
  model command to a view command then it'll throw a `RuntimeException`. For this, we have introduced a method in the controller's interface that is exposed to the view to verify all the commands.
    - This setup enforces a clear contract between the view and the controller: the view declares
      what commands it expects to use, and the controller confirms if they’re available. This can
      make both components easier to test, as each has a defined set of commands.
    - If new commands are added in the model, we'll immediately know if the view hasn’t accounted
      for them, allowing for a clean extension process.

```java
// the view:
controller.verifyCommands(this);
```
- A new method is added to the controller interface which is exposed to the view to invoke a command.
```java
// in the view:
controller.invokeCommand(Command c, ArgumentWrapper args);
```
