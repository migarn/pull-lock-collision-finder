# Collision Finder
Collision Finder is an application designed to check if there is a collision between lock and pull in door sash.

To check the collision the application determines pull's location mode (there are two modes: 'symmetrical' and 'standard'). Pull can be located symmetrical on sash when the pull's lowest point is located lower than 760mm from the sash lowest level. Otherwise, pull is located with 'standard' mode (pull's lowest point is located on the level of 760mm from the sash lowest level).

User can insert the values of: sash height, offset of handle from sash bottom, pull length and pull fixings spacing (lower and upper fixings location are automatically inserted by the application but they can also be edited by the user). Next, the user can choose lock type and after clicking the button 'Find Collision' the collision is checked and information about it along with sash drawing are displayed.

Locks data are parsed from the file 'locks.dat' which should look like:
```
lock name;cassette width,cassette offset;cassette width,cassette offset
lock name;cassette width,cassette offset
lock name;cassette width,cassette offset;cassette width,cassette offset;cassette width,cassette offset
...
```

Application preview:

![alt text](https://raw.githubusercontent.com/migarn/pull-lock-collision-finder/master/finder.png)
