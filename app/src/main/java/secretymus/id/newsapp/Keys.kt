package secretymus.id.newsapp

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}