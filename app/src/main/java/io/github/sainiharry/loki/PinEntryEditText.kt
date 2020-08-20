package io.github.sainiharry.loki

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class PinEntryEditText : AppCompatEditText {

    private var numOfChars = 0
    private var lineStrokeWidth = 0f
    private var spacing = 0

    private lateinit var linePaint: Paint
    private lateinit var textPaint: Paint

    constructor(context: Context?) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
        context?.let {
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.PinEntryEditText,
                defStyleAttr,
                0
            ).apply {
                try {
                    //todo update default value
                    lineStrokeWidth =
                        getDimensionPixelSize(R.styleable.PinEntryEditText_strokeWidth, 0).toFloat()
                    spacing = getDimensionPixelSize(R.styleable.PinEntryEditText_spacing, 0)
                } finally {
                    recycle()
                }
            }
        }

        // TODO: 20/08/20 move to constant
        numOfChars = attrs?.getAttributeIntValue(
            "http://schemas.android.com/apk/res/android",
            "maxLength",
            4
        ) ?: 0

        linePaint = Paint(paint)
        linePaint.strokeWidth = lineStrokeWidth

        textPaint = Paint(paint)

        setBackgroundResource(0)
    }

    override fun onDraw(canvas: Canvas?) {
        val availableWidth = (width - paddingRight - paddingLeft).toFloat()
        val startX = paddingLeft.toFloat()
        val bottom = (height - paddingBottom).toFloat()

        val numOfSpaces = numOfChars - 1
        val lineWidth = (availableWidth - (spacing * (numOfSpaces))) / numOfChars

        for (i in 0 until numOfChars) {
            val lineStart = startX + (lineWidth + spacing) * i
            canvas?.drawLine(lineStart, bottom, lineStart + lineWidth, bottom, linePaint)
        }
    }
}