package com.meesho.jakewarton.domain

import MockTestUtil
import com.meesho.jakewarton.data.db.Repository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveQrScanResultTest {
    @MockK
    private lateinit var repository: Repository

    @MockK
    private lateinit var parseScanResult: ParseScanResult

    @MockK
    private lateinit var sessionTimer: SessionTimer

    @Before
    fun setUp() {
        MockKAnnotations.init(this,relaxed = true)
    }

    @Test
    fun `test saves given raw scan data in db & starts timer`() = runBlocking {
        val givenStartTime = System.currentTimeMillis()
        val givenStartSession=MockTestUtil.qrScanStartRawData()
        val givenActiveSession=MockTestUtil.activeSession()

        // when
        coEvery { parseScanResult.parse(givenStartSession) }
            .returns(givenActiveSession)

        // Invoke
        val activeSession =parseScanResult.parse(givenStartSession)
        repository.saveQrScanResult(scanResult = activeSession,startTime = givenStartTime)
        sessionTimer.start()

        coVerify(exactly = 1) { parseScanResult.parse(givenStartSession)}
        coVerify(exactly = 1) { repository.saveQrScanResult(scanResult = activeSession,startTime = givenStartTime)}
        coVerify(exactly = 1) { sessionTimer.start()}
    }
}