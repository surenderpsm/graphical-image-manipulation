# 1. ArgumentHandler
## Premise
Until now, argument handling has been delagated to each command. The controller simply relays user input to the model, and the model executes the called command and passes the arguments without any validation. This is a very simple and compartmentalized approach which was desired because of ease of extension. But, as more and more commands were added, there was a need for efficiency. Relaying the command arguments without any validation leads to unnecessary usage of the model package. Hence, we decided to delegate the argument handling to the controller.
## Overview
Following is a brief overview of the changes and additions made:
1. The controller is now tasked with argument handling.
2. The CommandFactory also houses information about the arguments that each command takes.
3. An argument signature can be defined using the `Signature` class. This takes in a variable number of `ArgumentType` enum constants.
4. Arguments are now packaged by using the `ArgumentWrapper` class.
5. The `ArgumentWrapper` class be constructed to accept arguments that match a certain `Signature`.
## `arguments` package
The `arguments` package defines the various utility methods for setting up and handling arguments. It is composed of:
- An `Argument` interface.
- Concrete implementations of the argument interface are different type of supported
arguments. We require `int`, `String` and `File` types for our arguments. Hence, we add
the 3 concrete implementations of the `Argument` interface.
- An `ArgumentWrapper` class consists of an internal `Map` to map the argument (ordering)
number to the `Argument` value. An instance of this class can be constructed by passing multiple `Argument` types (variable length).
- Additionally, the `ArgumentWrapper` can be constructed using an expected signature. Let's say we need 3 arguments where the order of the expected signatures are `File`, `String` and `Integer`. We can define this for an `ArgumentWrapper`. When we input the arguments into the `ArgumentWrapper`, an `IllegalArgumentException` is thrown if the types don't match the signature.

```java

```

# Major Refactoring operations
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
- We also want to ensure existing code remains minimally unchanged.

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


```java
import utils.arguments.ArgumentWrapper;
import utils.arguments.FileArgument;
import utils.arguments.StringArgument;
// in the controller/view:
ArgumentWrapper args = new ArgumentWrapper(new FileArgument("/res/app/newkoala.png"), new StringArgument("koala"));

// in the model (for example):
args.getFileArgument(0) // returns a file.
args.getStringArgument(1) // returns a string.
// incorrect calling results in IllegalArgumentException
args.getIntArgument(1) // throws IllegalArgumentException.
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
## Details of the refactor
### IModel
- This interface is the main interface that is implemented by the `Model` facade class.
- It currently extends existing interfaces `ModelRunner`, `HistogramCacheProvider` and `ImageCacheProvider`.
- This interface defines a method `getAllCommandRunnables()` which returns a `Map<String, Runnable>` to inform the calling object about its available commands and how to call them.
