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
