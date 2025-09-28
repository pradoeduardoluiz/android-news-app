package com.prado.eduardo.luiz.newsapp.auth

import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import com.prado.eduardo.luiz.newsapp.ui.auth.BiometricAuthManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BiometricAuthManagerTest {

    private lateinit var activity: FragmentActivity
    private lateinit var biometricAuthManager: BiometricAuthManager
    private lateinit var mockBiometricManager: BiometricManager

    @Before
    fun setup() {
        activity = mockk(relaxed = true)
        mockBiometricManager = mockk()

        mockkStatic(BiometricManager::class)
        every { BiometricManager.from(activity) } returns mockBiometricManager

        biometricAuthManager = BiometricAuthManager(activity)
    }

    @After
    fun tearDown() {
        unmockkStatic(BiometricManager::class)
    }

    @Test
    fun `isBiometricAvailable should return true when biometric is available`() {
        // Given
        every {
            mockBiometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        } returns BiometricManager.BIOMETRIC_SUCCESS

        // When
        val result = biometricAuthManager.isBiometricAvailable()

        // Then
        assertTrue(result)
    }

    @Test
    fun `isBiometricAvailable should return false when biometric is not available`() {
        // Given
        every {
            mockBiometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        } returns BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE

        // When
        val result = biometricAuthManager.isBiometricAvailable()

        // Then
        assertFalse(result)
    }

    @Test
    fun `isBiometricAvailable should return false when biometric is not enrolled`() {
        // Given
        every {
            mockBiometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        } returns BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED

        // When
        val result = biometricAuthManager.isBiometricAvailable()

        // Then
        assertFalse(result)
    }

    @Test
    fun `isBiometricAvailable should return false when biometric is unavailable`() {
        // Given
        every {
            mockBiometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        } returns BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED

        // When
        val result = biometricAuthManager.isBiometricAvailable()

        // Then
        assertFalse(result)
    }
}
