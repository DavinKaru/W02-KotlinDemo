package au.edu.swin.sdmd.w02_kotlindemo

data class Counter(val name: String, var count: Int = 0) {
    fun increment() {
        count += 1
    }

    // Custom accessor
    val isZero: Boolean
        get() = count == 0
}