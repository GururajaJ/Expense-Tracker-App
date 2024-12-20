import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.io.InputStream
import java.io.FileInputStream
import java.io.IOException

class ExpensePredictionModel(context: Context) {
    private var interpreter: Interpreter? = null

    init {
        try {
            // Open the model from the assets folder
            val assetManager = context.assets
            val modelFile = assetManager.open("weekly_expense_model.tflite")
            val modelBuffer = loadModelFile(modelFile)

            // Initialize the interpreter with the model
            interpreter = Interpreter(modelBuffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Function to load the model file into a MappedByteBuffer
    private fun loadModelFile(fileDescriptor: InputStream): MappedByteBuffer {
        // Convert InputStream to FileInputStream to access FileChannel
        val fileChannel = (fileDescriptor as FileInputStream).channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size())
    }

    // Function to predict the expense based on the week number
    fun predictExpense(weekNumber: Float): Float {
        val input = floatArrayOf(weekNumber)
        val output = FloatArray(1)

        interpreter?.run(input, output)
        return output[0]
    }

    // Close the interpreter when no longer needed
    fun close() {
        interpreter?.close()
    }
}
