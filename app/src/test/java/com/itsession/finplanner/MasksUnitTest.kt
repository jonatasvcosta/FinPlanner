package com.itsession.finplanner

import com.itsession.finplanner.presentation.domain.ExtensionMethods.toFinancialValue
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MasksUnitTest {
    @Test
    fun financialValue_isCorrect() {
        assertEquals("233", (233.0).toFinancialValue())
        assertEquals("1 K", (1000.0).toFinancialValue())
        assertEquals("1.25 K", (1247.0).toFinancialValue())
        assertEquals("5.75 K", (5751.0).toFinancialValue())
        assertEquals("5.80 K", (5795.1).toFinancialValue())
        assertEquals("1.23 M", (1230499.0).toFinancialValue())
    }
}