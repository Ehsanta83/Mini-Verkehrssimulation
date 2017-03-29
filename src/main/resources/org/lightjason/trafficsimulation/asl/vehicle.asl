// initial-goal
!main.

// initial plan (triggered by the initial-goal) - calculates the initial route route
+!main
    <-
    !movement/drive/forward
.


// --- movement plans ------------------------------------------------------------------------------------------------------------------------------------------


// plan to deal with force information
+!movement/force(D)
    : >>( force(F), D )
        <-
            generic/print("force", F)
.

// drive straight forward into the direction of the goal-position
+!movement/drive/forward
    <-
        generic/print( "drive forward in cycle [", Cycle, "]" );
        move/forward();
        !movement/drive/forward
.

// drive straight forward fails than sleep and hope everything will be
// fine later, wakeup plan will be trigger after sleeping
-!movement/drive/forward
    <-
        T = math/statistic/randomsimple();
        T = T * 10 + 1;
        T = math/min( T, 5 );
        generic/print( "drive right fails in cycle [", Cycle, "] wait [", T,"] cycles" );
        generic/agent/sleep(T)
.

// if the agent is not driveing because speed is
// low the agent increment the current speed
+!movement/standstill
    : >>attribute/speed( S )
        <-
            generic/print( "standstill - increment speed with 1 in cycle [", Cycle, "]" );
            S++;
            +attribute/speed( S );
            !movement/drive/forward
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------

// --- other calls ---------------------------------------------------------------------------------------------------------------------------------------------


// is called if || current position - goal-position || <= near-by
// the exact position of the goal will be skipped, so the agent
// is driving to the next position
+!position/achieve(P, D)
    <-
        generic/print( "position [", P, "] achieved with distance [", D, "] in cycle [", Cycle, "]" );
        route/next
.

// is called if the agent drives beyonds the goal-position, than
// the speed is set to 1 and we try go back
+!position/beyond(P)
    <-
        generic/print( "position beyond [", P, "] - set speed to 1 in cycle [", Cycle, "]" );
        +attribute/speed( 1 );
        route/next;
        !movement/drive/forward
.

// if the agent is wake-uped the speed is set to 1 and the agent starts driving
// to the next goal-position
+!wakeup
    <-
        generic/print("wakeup - set speed to 1 in cycle [", Cycle, "]");
        +attribute/speed( 1 );
        !movement/drive/forward
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------
