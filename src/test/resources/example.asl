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

+!main <-
    generic/print("hello")
.

+!vehicleway( this(X), position(Y) ) <-
    generic/print(X,Y)
.

+!vehicle( speed(S), type(T), visibility(V), vehiclestrafficlightcolor(Vtl), pedestrianstrafficlightcolor(Ptl) )
    : S == 1
    <-
    generic/print( S, T, V, Vtl, Ptl)
    !fooplan

    : S == 2
    <-
      ...
.

+!fooplan <- generic/print("hallo")








!rot.

+!rot <- ..... !rotgelb.

+!rotgelb <- ....!gruen.

+!gruen <- .... !gelb.

+!gelb <- .... !rot.
