import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val MAX_VALUE: Int = 1000
const val MIN_VALUE: Int = -1000

const val SPACE = ""
const val SUBTRACTION = "-"
const val ADDITION = "+"
const val MULTIPLICATION = "*"
const val DIV = "/"
const val MOD = "%"

const val SUBTRACTION_FUNC_NAME: String = "subtraction"
const val ADDITION_FUNC_NAME: String = "addition"
const val MULTIPLICATION_FUNC_NAME: String = "multiply"
const val DIV_FUNC_NAME: String = "div"
const val MOD_FUNC_NAME: String = "mod"

val TYPE: List<String> = listOf("void", "int", "bool", "float", "double")
val MODIFIER: List<String> = listOf("short", "long", "unsigned")
val FUNCNAME: List<String> = listOf("main", "subtraction", "addition", "multiply", "div", "mod")
val ARITHMETIC_OPERATIONS: List<String> = listOf("+", "-", "/", "%")
val LOGICAL_OPERATIONS: List<String> = listOf("!", "&&", "||")
val RELATIONAL_OPERATIONS: List<String> = listOf("<", ">", "<=", ">=", "==", "!=")
val SPECIAL_OPERATIONS: List<String> = listOf("++", "--")
val IDENTIFIER: List<String> = listOf("a", "b", "c")
val CARRIAGE_RETURN = "\n"
val EQUALLY = "="
val COMMA = ","
val DOT = "."
val SEMICOLON = ";"
val COLON = ":"
val LIBRARY: List<String> = listOf("stdio.h")
val INCLUDE: List<String> = listOf("#include <", ">")
val BRACKETS: List<String> = listOf("(", ")", "{", "}", "[", "]")
val TAB = " "

//пофиксить повторное подключение либов
fun Include(program: String) : String {
    if ( randBool() )
        return Include("$program${INCLUDE[0]}${LIBRARY[rand(0, LIBRARY.size)]}${INCLUDE[1]}$CARRIAGE_RETURN")
    return "$program${INCLUDE[0]}${LIBRARY[ rand(0, LIBRARY.size) ]}${INCLUDE[1]}$CARRIAGE_RETURN"
}

fun Arguments(program: String) : String {
    if ( randBool() )
        return Arguments("$program${TYPE[ rand(1, TYPE.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}$COMMA ")
    return "$program${TYPE[ rand(1, TYPE.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}"
}

fun ArithmeticExceptionAddition(program: String) : String {
    if ( randBool() )
        return ArithmeticExceptionAddition("$program ${ARITHMETIC_OPERATIONS[ rand(0, ARITHMETIC_OPERATIONS.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    return "$program ${ARITHMETIC_OPERATIONS[ rand(0, ARITHMETIC_OPERATIONS.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}"
}

fun ArithmeticExpression(program: String) : String {
    var prog = "$program ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]} $EQUALLY ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}"
    if ( randBool() )
        ArithmeticExceptionAddition(prog)
    return "$prog$SEMICOLON"
}

fun Code(program: String) : String {
    if ( randBool() )
        return Code(program)
    return ArithmeticExpression(program)
}

fun Function(program: String) : String {
    return "$program$CARRIAGE_RETURN${TYPE[ rand(0, TYPE.size) ]} ${FUNCNAME[ rand(1, FUNCNAME.size) ]}${BRACKETS[0]}${Arguments(SPACE)}${BRACKETS[1]} ${BRACKETS[2]}${CARRIAGE_RETURN}$TAB${Code(SPACE)}$CARRIAGE_RETURN${BRACKETS[3]}"
}

fun rand(from: Int, to: Int) : Int {
    val random = Random()
    return random.nextInt(to - from) + from
}

fun randBool() : Boolean {
    if ( rand(0, 2).equals(1) )
        return true
    return false
}

fun operator() : String {
    val c = rand(0, 5)
    var r = (SPACE)
    if (c.equals(0)) {
        r = SUBTRACTION
        r
    }
    if (c.equals(1)) {
        r = ADDITION
        r
    }
    if (c.equals(2)) {
        r = MULTIPLICATION
        r
    }
    if (c.equals(3)) {
        r = DIV
        r
    }
    if (c.equals(4)) {
        r = MOD
        r
    }
    return r
}

fun funcName(r: String) : String {
    var q = SPACE
    if ( r == SUBTRACTION ) {
        q = SUBTRACTION_FUNC_NAME
        q
    }
    if ( r == ADDITION ) {
        q = ADDITION_FUNC_NAME
        q
    }
    if ( r == MULTIPLICATION ) {
        q = MULTIPLICATION_FUNC_NAME
        q
    }
    if ( r == DIV ) {
        q = DIV_FUNC_NAME
        q
    }
    if ( r == MOD ) {
        q = MOD_FUNC_NAME
        q
    }
    return q
}

fun printFun(a: Int, b: Int, c: String) {
    var program = SPACE
    program = Include(program)
    program = Function(program)

//    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//    var file = File("func_$time.c")
    var file = File("func.c")
//    file.writeText("#include <stdio.h>\n\nint $funcName(int a, int b) {\n    return a $c b;\n}\n\nint main() {\n    return $funcName($a, $b);\n}")
    file.writeText(program)

//    val funcName = funcName(c)
    println(program)
/*    val x: String = time as String
    val exec: Execute = Execute()

    exec.Execut(x)
*/
}

//%
fun calc(a: Int, b: Int, c: String) : Int {

    var q = a - b
    if ( c == SUBTRACTION ) {
        q = a - b
        q
    }
    if ( c == ADDITION ) {
        q = a + b
        q
    }
    if ( c == MULTIPLICATION ) {
        q = a * b
        q
    }
    if ( c == DIV ) {
        q = a / b
        q
    }
    if ( c == MOD ) {
        q = a % b
        q
    }
    return q
}

fun printQuest(a: Int, b: Int, c: String) {
    print( "your question: " )
    val question = readLine()!!.toInt()
    if (question != null) {
        val quest = calc(a, b, c)
        println(quest)
        println( question.equals(quest) )
    }
}
fun main(args: Array<String>) {
    val a = rand(MIN_VALUE, MAX_VALUE)
    val b = rand(MIN_VALUE, MAX_VALUE)
    val c = operator()
    printFun(a, b, c)
    printQuest(a, b, c)
}
