package io.github.sainiharry.pinentryedittext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatEditText

class PinEntryEditText : AppCompatEditText {

    private var numOfChars = 0
    private var lineStrokeWidth = 0f
    private var spacing = 0
    private var strokeColor = Color.BLACK
    private var textVerticalPadding = 0f

    private lateinit var linePaint: Paint

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
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
                textVerticalPadding = getDimensionPixelSize(
                    R.styleable.PinEntryEditText_textVerticalPadding,
                    0
                ).toFloat()
                strokeColor = getColor(R.styleable.PinEntryEditText_strokeColor, Color.BLACK)
            } finally {
                recycle()
            }
        }

        // TODO: 20/08/20 move to constant
        numOfChars = attrs?.getAttributeIntValue(
            "http://schemas.android.com/apk/res/android",
            "maxLength",
            4
        ) ?: 0

        linePaint = Paint()
        linePaint.strokeWidth = lineStrokeWidth
        linePaint.color = strokeColor

        setBackgroundResource(0)

        // Disable copy-paste
        super.setCustomSelectionActionModeCallback(object: ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = false

            override fun onDestroyActionMode(mode: ActionMode?) {
            }
        })

        //Move cursor to end of text
        super.setOnClickListener {
            setSelection(text?.length ?: 0)
        }
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

        val textWidth = paint.measureText("*")
        val inputLength = text?.length ?: 0
        for (i in 0 until inputLength) {
            val lineStart = startX + (lineWidth + spacing) * i
            val lineMiddle = lineStart + (lineWidth / 2)
            canvas?.drawText("*", lineMiddle - (textWidth / 2), bottom - textVerticalPadding, paint)
        }
    }
}