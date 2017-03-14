!main.

+!main <-
    generic/print("Hallo")
.

+!vehicleway( this(X), position(Y) ) <-
    generic/print(X,Y)
.

+!vehicle( speed(S), type(T), visibility(V), vehiclestrafficlightcolor(Vtl), pedestrianstrafficlightcolor(Ptl) )
    <-
    generic/print( S, T, V, Vtl, Ptl)
.