import java.io.File
import java.lang.Integer.parseInt
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val MAX_VALUE: Int = 20
const val MIN_VALUE: Int = -MAX_VALUE

val SPACE = ""
val TYPE: List<String> = listOf("void", "int", "bool", "float", "double")
val MODIFIER: List<String> = listOf("short", "long", "unsigned")
val FUNCNAME: List<String> = listOf("main", "subtraction", "addition", "multiply", "div", "mod")
val ARITHMETIC_OPERATIONS: List<String> = listOf("+", "-", "/", "%")
val LOGICAL_OPERATIONS: List<String> = listOf("!", "&&", "||")
val RELATIONAL_OPERATIONS: List<String> = listOf("<", ">", "<=", ">=", "==", "!=")
val SPECIAL_OPERATIONS: List<String> = listOf("++", "--")
val BITWISE_OPERATIONS: List<String> = listOf("<<", ">>", "|", "&")
val IDENTIFIER: List<String> = listOf("a", "b", "c", "d", "e", "f", "g")
val CARRIAGE_RETURN = "\n"
val EQUALLY = "="
val COMMA = ","
val DOT = "."
val SEMICOLON = ";"
val COLON = ":"
val END_OF_LINE = "${SEMICOLON}${CARRIAGE_RETURN}"
val LIBRARY: List<String> = listOf("stdio.h")
val INCLUDE: List<String> = listOf("#include <", ">")
val BRACKETS: List<String> = listOf("(", ")", "{", "}", "[", "]")
val TAB = "    "
val RETURN = "return"

fun Identifier(index: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(IDENTIFIER[index])
    return program_
}

fun Identifier(program: MutableList<String>, args: Array<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    val index = rand(0, parseInt(args[1]))
    val value = "${IDENTIFIER[index]} $EQUALLY"
    for (i: Int in 0..(program.size - 1))
        if (program[i] == value)
            for (j: Int in (i + 1)..(program.size - 1))
                if (program[j] == END_OF_LINE) {
                    program_.addAll(Identifier(index))
                    return program_
                }
    return Identifier(program, args)
}

fun OperationType(args: Array<String>) : MutableList<String> {
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    for ( i: Int in 4..(args.size - 1) )
        OPERATIONS_TYPE.add( args[i] )
    return OPERATIONS_TYPE
}

fun Operation(args: Array<String>) : MutableList<String> {
    val OPERATIONS_TYPE: MutableList<String> = mutableListOf()
    OPERATIONS_TYPE.addAll( OperationType(args) )

    val program_: MutableList<String> = mutableListOf()
    program_.add(" ${OPERATIONS_TYPE[rand(0, OPERATIONS_TYPE.size)]} ")
    return program_
}

fun check(count: Int, index: Int, args: Array<String>) : Boolean {
    if ( count < parseInt(args[index]) )
        return true
    return false
}

fun Include(libr_numb: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add("${INCLUDE[0]}${LIBRARY[libr_numb]}${INCLUDE[1]}$CARRIAGE_RETURN")
    return program_
}

fun Arguments(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if (randBool()) {
        program_.addAll(Arguments(program_))
        program_.add("$COMMA ")
    }
    program_.add("${TYPE[ rand(1, TYPE.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    return program_
}

/*fun ArithmeticExpressionAddition(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( randBool() )
        program_.addAll( ArithmeticExpressionAddition(program_) )
    program_.add(" ${ARITHMETIC_OPERATIONS[ rand(0, ARITHMETIC_OPERATIONS.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    return program_
}

fun ArithmeticExpression(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add("$TAB${IDENTIFIER[ rand(0, IDENTIFIER.size) ]} $EQUALLY ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    if ( randBool() )
        program_.addAll( ArithmeticExpressionAddition(program_) )
    return program_
}

fun BitwiseExpressionAddition(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( randBool() )
        program_.addAll( BitwiseExpressionAddition(program_) )
    program_.add(" ${BITWISE_OPERATIONS[ rand(0, BITWISE_OPERATIONS.size) ]} ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    return program_
}

fun BitwiseExpression(program: MutableList<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add("$TAB${IDENTIFIER[ rand(0, IDENTIFIER.size) ]} $EQUALLY ${IDENTIFIER[ rand(0, IDENTIFIER.size) ]}")
    if ( randBool() )
        program_.addAll( BitwiseExpressionAddition(program_) )
    return program_
}
*/

fun Brackets(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.add(BRACKETS[0])
    program_.addAll(Expression(program, args, count))
    program_.add(BRACKETS[1])
    return program_
}

fun Atom(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( check(count, 3, args) )
        if ( randBool() && check(count + 2, 2, args))
            program_.addAll(Brackets(program, args, count))
        else
            if ( randBool() )
                program_.addAll(Identifier(program, args))
            else
                program_.add("${rand(1, MAX_VALUE)}")
    return program_
}

fun ExpressionAddition(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( check(count, 3, args) ) {
        program_.addAll(Operation(args))
        program.addAll(program_)
        program_.addAll(Atom(program, args, count))
        val count_ = count + 1
        if ( randBool() )
            program_.addAll(ExpressionAddition(program, args, count_ + 1))
    }
    return program_
}

fun Expression(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( count.equals(0) || check(count, 3, args) ) {
        if ( randBool() )
            program_.addAll(Identifier(program, args))
        else
            program_.add("${rand(1, MAX_VALUE)}")
        program.addAll(program_)
        program_.addAll( ExpressionAddition(program, args, count + 1) )
    }
    return program_
}

//пофиксить кол-во строк-выражений
fun Statement(program: MutableList<String>, args: Array<String>, count: Int) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    if ( check(count, 2, args) ) {
        program_.add(TAB)
        program_.add("${IDENTIFIER[rand(0, parseInt(args[1]))]} ${EQUALLY}")
        program_.add(" ")
        program.addAll(program_)
        program_.addAll( Expression(program, args, 0) )
        program_.add(END_OF_LINE)
        program.addAll(program_)
        program_.addAll( Statement(program, args, count + 1) )
    }
    return program_
}

/*fun Code(program: MutableList<String>, args: Array<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
//    val lines_numb = parseInt(args[2])
//    val operations_numb = parseInt(args[3])
//    println(OPERATIONS_TYPE.size)
//    if ( randBool() )
//        program_.addAll( Code(program_) )
//    program_.addAll( ArithmeticExpression(program_) )
    program_.addAll( Statement(program, args, 0) )
    return program_
}

fun Function(program: MutableList<String>) : MutableList<String> {
    val space: MutableList<String> = mutableListOf()
    val program_: MutableList<String> = mutableListOf()
    program_.addAll( Include(0) )
    program_.add("$CARRIAGE_RETURN${TYPE[ rand(0, TYPE.size) ]} ${FUNCNAME[ rand(1, FUNCNAME.size) ]}${BRACKETS[0]}")
    program_.addAll(Arguments(space))
    program_.add("${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
//    program_.addAll(Code(space))
    program_.add("${BRACKETS[3]}")
    return program_
}
*/

fun firstTask(args: Array<String>) : MutableList<String> {
    val program_: MutableList<String> = mutableListOf()
    program_.addAll(Include(0))
    program_.add("$CARRIAGE_RETURN${TYPE[1]} ${FUNCNAME[0]}${BRACKETS[0]}${BRACKETS[1]} ${BRACKETS[2]}$CARRIAGE_RETURN")
    val initialized_args = rand(0, parseInt(args[1]) - 1)   //номера инициализированных переменных
    val uninitialized_args = parseInt(args[1]) - 2          //номера неинициализированных переменных

    for ( i in 0..initialized_args ) {
        program_.add("${TAB}${MODIFIER[2]} ${TYPE[1]} ")
        program_.add("${IDENTIFIER[i]} ${EQUALLY}")
        program_.add(" ${rand(0, MAX_VALUE)}")
        program_.add(END_OF_LINE)
    }

    var i = initialized_args + 1
    program_.add("${TAB}${MODIFIER[2]} ${TYPE[1]}")
    for ( j: Int in i..uninitialized_args ) {
        program_.add(" ${IDENTIFIER[j]}${COMMA}")
        i++
    }
    program_.add(" ${IDENTIFIER[i]}${END_OF_LINE}")
    val program: MutableList<String> = mutableListOf()
    program.addAll(program_)
    program_.addAll( Statement(program, args, 0) )
    program_.add("${TAB}${RETURN} 0${END_OF_LINE}")
    program_.add(BRACKETS[3])
    return program_
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

fun printFun(args: Array<String>) {
    val program: MutableList<String> = mutableListOf()
    if ( parseInt(args[0]).equals(1) ) {
        program.addAll( firstTask(args) )

//        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
//        var file = File("func_$time.c")

        var file = File("func.c")
        file.writeText(program.joinToString(SPACE))
        println(program.joinToString(SPACE))
    }
}

fun main(args: Array<String>) {
    /*var args_: Array<String> = arrayOf(String())
    var i = 0
    var a : String = ""
    val string = readLine()!!
    for (i: Int in 0..string.length)
        args_[i] = ""*/
//                string[i].toString()
    //        args_.set(i, string[i].toString())
    /*while ( a != "\n" ) {
        a = readLine()!!
        args_.set(i, a)
        i++
    }*/
//    println(args_)
//    args_.set(0, task_numb)
/*    args_[0] = "1"
    args_[1] = "4"
    args_[2] = "5"
    args_[3] = "5"

    args_[4] = "<<"
    args_[5] = ">>"
    args_[6] = "-"
    args_[7] = "+"
    args_[8] = "*"
    args_[9] = "&"
    args_[10] = "|"*/

    printFun(args)
}