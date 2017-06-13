/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason TrafficSimulation                              #
 * # Copyright (c) 2016-17, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

!main.

+!main
    <-  generic/print("hello vehicle");
    route/set/start( 25, 25 );
    !movement/forward
.

// move straight forward into the direction of the goal-position
+!movement/forward
    <-
        generic/print( "move forward in cycle [", Cycle, "]" );
        move/forward();
        !movement/forward
.

// move straight forward fails than go forward left
-!movement/forward
    <-
        generic/print( "move forward fails in cycle [", Cycle, "]" );
        !movement/forwardleft
.

// move forward left - direction 45 degree to the goal position
+!movement/forwardleft
    <-
        generic/print( "move forward left in cycle [", Cycle, "]" );
        move/forwardleft();
        !movement/forward
.

// move forward left fails than go right
-!movement/forwardleft
    <-
        generic/print( "move forward left fails in cycle [", Cycle, "]" );
        !movement/forwardright
.

// move forward right - direction 45 degree to the goal position
+!movement/forwardright
    <-
        generic/print( "move forward right in cycle [", Cycle, "]" );
        move/forwardright();
        !movement/forward
.

// move forward right fails than sleep and hope everything will be
// fine later, wakeup plan will be trigger after sleeping
-!movement/forwardright
    <-
        T = math/statistic/randomsimple();
        T = T * 10 + 1;
        T = math/min( T, 5 );
        generic/print( "move right fails in cycle [", Cycle, "] wait [", T,"] cycles" );
        agent/sleep(T)
.

+!vehicle( speed(S), type(T), visibility(V), vehiclestrafficlightcolor(Vtl), pedestrianstrafficlightcolor(Ptl) )
    <-
    generic/print( S, T, V, Vtl, Ptl)
.