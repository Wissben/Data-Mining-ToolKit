package Algorithms.Apriori

import java.util.*

class InstanceApriori {


    var transactions: ArrayList<TreeSet<String>>
    internal var labeledTransactions: ArrayList<TreeSet<String>>

    init {
        transactions = ArrayList()
        labeledTransactions = ArrayList()

    }

    fun addTransaction(id: Int, items: Array<String>) {
        val tmp = TreeSet<String>()
        for (i in items.indices) {
            tmp.add(items[i])
        }
        transactions.add(id, tmp)
    }

    fun addLabeledTransaction(id: Int, items: Array<String>) {
        val tmp = TreeSet<String>()
        for (i in items.indices) {
            tmp.add(items[i])
        }
        labeledTransactions.add(id, tmp)
    }

    override fun toString(): String {
        var res = ""
        for (i in transactions.indices) {
            res += "T_id $i :  "
            for (key in transactions[i]) {
                res += "$key , "
            }
            res += "\n"
        }
        return res
    }

    fun printTransactions() {
        for (key in transactions) {
            println(key)
        }
    }


}
