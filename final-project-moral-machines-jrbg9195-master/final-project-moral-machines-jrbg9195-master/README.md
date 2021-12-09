## Moral Machines
The idea of Moral Machines is based on the **Trolley Dilemma**, a fictional scenario presenting a decision-maker with a moral dilemma: choosing ''the lesser of two evils''. The scenario entails an autonomous car whose brakes fail at a pedestrian crossing. As it is too late to relinquish control to the car's passengers, the car needs to make a decision based on the facts available about the situation.

Feel free to familiarize yourself with the problem and explore different scenarios on [MIT's Moral Machine Website](https://www.moralmachine.net/).

An **Ethical Engine** is created which is a program designed to explore different precarious scenarios. An algorithm is carefully built to decide between the life of the car's passengers and the life of the pedestrians. Through simulations, the results of the decision-making algorithm can be audited. Users of the program are also able to judge the outcomes themselves through an interactive mode.


### Scenario Import and Interactive Mode Test

The [tests folder](./tests/) comprises the following files to test your code locally. 

- [config_3.csv](tests/config_3.csv): contains 3 example scenarios and is basis for the interactive mode test described below.
- [config_10.csv](tests/config_10.csv): contains 10 example scenarios.
- [config_100.csv](tests/config_100.csv): contains 100 example scenarios.
- [in_interactive_config_3](tests/in_interactive_config_3): is an example user input file.

The config files comprise a range of different, valid scenarios. Thus, they do not contain any errors, which is why your program should be able to correctly import and display them.

To test the logic and the expected output of your interactive mode, run your program with the following parameters:

```
java EthicalEngine -i -c tests/config_3.csv < tests/in_interactive_config_3 > output
```

This command will run the EthicalEngine main method in interactive mode. It will import three scenarios from the [config_3.csv](./tests/config_3.csv) file and pipe in the pre-defined user input from the [in_interactive_config_3](./tests/in_interactive_config_3). The output of your program will be written to <i>output</i>. Inspect its contents as it may contain any errors your program execution may have encountered and compare it with the contents of [out_interactive_config_3](./tests/out_interactive_config_3), which holds the expected program output.
