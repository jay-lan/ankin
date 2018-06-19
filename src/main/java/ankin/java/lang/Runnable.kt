package ankin.java.lang

/**
 * 帝鲮于 2018/3/10创建.
 */
operator fun Runnable.invoke() {
    this.run()
}