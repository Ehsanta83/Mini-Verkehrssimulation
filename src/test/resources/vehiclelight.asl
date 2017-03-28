!main.

+!main
    <- generic/print("hello vehicle light");
    state/next;
    +extern/iam(red)
.

+!vehicletrafficlight(color (C), duration(D), numberofcarsinline(N))
    <- generic/print(C, D, N)
.
