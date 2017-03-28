!main.

+!main
    <- generic/print("hello vehicle light");
    +extern/iam(red)
.

+!vehicletrafficlight(color (C), duration(D), numberofcarsinline(N))
    <- generic/print(C, D, N)
.
