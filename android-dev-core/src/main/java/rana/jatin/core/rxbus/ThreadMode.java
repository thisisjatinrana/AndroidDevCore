package rana.jatin.core.rxbus;

public enum ThreadMode {
    /**
     * current thread
     */
    CURRENT_THREAD,

    /**
     * android main thread
     */
    MAIN_THREAD,


    /**
     * new thread
     */
    NEW_THREAD,

    /**
     * computation thread
     */
    COMPUTATION_THREAD,

    /**
     * io thread
     */
    IO_THREAD
}
