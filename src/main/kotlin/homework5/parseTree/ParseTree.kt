package homework5.parseTree

import java.io.File
import java.lang.IllegalArgumentException

enum class Operation(val sign: String) {
    Plus("+"),
    Minus("-"),
    Division("/"),
    Multiplication("*")
}

interface ParseTreeNode {
    fun getString(height: Int): String
    fun getValue(): Int
}

class ParseTreeValueNode(private val value: Int = 0) : ParseTreeNode {
    override fun getString(height: Int): String {
        return "...".repeat(height) + value.toString() + "\n"
    }

    override fun getValue(): Int = value
}

class ParseTreeOperationNode(
    private val operator: Operation,
    private val leftNode: ParseTreeNode,
    private val rightNode: ParseTreeNode
) : ParseTreeNode {
    override fun getString(height: Int): String {
        return "...".repeat(height) + operator.sign + "\n" +
                leftNode.getString(height + 1) +
                rightNode.getString(height + 1)
    }

    override fun getValue(): Int {
        return when (operator) {
            Operation.Plus -> leftNode.getValue() + rightNode.getValue()
            Operation.Minus -> leftNode.getValue() - rightNode.getValue()
            Operation.Division -> leftNode.getValue() / rightNode.getValue()
            Operation.Multiplication -> leftNode.getValue() * rightNode.getValue()
        }
    }
}

class ParseTree(pathToFile: String) {
    private val root: ParseTreeNode

    init {
        val inputList = File(pathToFile).readText()
            .replace("(", "")
            .replace(")", "")
            .split(" ")
        root = parseOperands(inputList).node
    }

    fun getValue(): Int = root.getValue()
    fun getString(): String = root.getString(0)

    data class TransferData(val renewedList: List<String>, val node: ParseTreeNode)

    private fun parseOperands(operandList: List<String>): TransferData {
        return when {
            operandList.first().toIntOrNull() is Int -> {
                TransferData(operandList.drop(1), ParseTreeValueNode(operandList.first().toInt()))
            }
            else -> {
                val currentOperator = when (operandList.first()) {
                    "+" -> Operation.Plus
                    "-" -> Operation.Minus
                    "/" -> Operation.Division
                    "*" -> Operation.Multiplication
                    else -> {
                        throw IllegalArgumentException("Incorrect operator or Incorrect expresion")
                    }
                }
                var result = parseOperands(operandList.drop(1))
                val leftNode = result.node
                result = parseOperands(result.renewedList)
                val rightNode = result.node
                return TransferData(result.renewedList, ParseTreeOperationNode(currentOperator, leftNode, rightNode))
            }
        }
    }
}
