package com.prado.eduardo.luiz.newsapp.auth

import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import com.prado.eduardo.luiz.newsapp.ui.auth.AuthViewModel
import com.prado.eduardo.luiz.newsapp.ui.auth.BiometricAuthManager
import com.prado.eduardo.luiz.newsapp.ui.auth.AuthIntent
import com.prado.eduardo.luiz.newsapp.ui.auth.AuthState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var biometricAuthManager: BiometricAuthManager
    private lateinit var dispatchersProvider: DispatchersProvider
    private lateinit var authViewModel: AuthViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        biometricAuthManager = mockk()
        dispatchersProvider = mockk {
            every { io } returns testDispatcher
            every { ui } returns testDispatcher
            every { default } returns testDispatcher
        }
        authViewModel = AuthViewModel(biometricAuthManager, dispatchersProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `publish Initialize should set Authenticated state when biometric is not available`() =
        runTest {
            // Given
            every { biometricAuthManager.isBiometricAvailable() } returns false

            // When
            authViewModel.publish(AuthIntent.Initialize)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val state = authViewModel.uiState.first()
            assertEquals(AuthState.Authenticated, state.authState)
        }

    @Test
    fun `publish Initialize should perform biometric auth when available and succeed`() = runTest {
        // Given
        every { biometricAuthManager.isBiometricAvailable() } returns true
        coEvery { biometricAuthManager.authenticate() } returns BiometricAuthManager.BiometricResult.Success

        // When
        authViewModel.publish(AuthIntent.Initialize)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = authViewModel.uiState.first()
        assertEquals(AuthState.Authenticated, state.authState)
        verify { biometricAuthManager.isBiometricAvailable() }
    }

    @Test
    fun `publish Initialize should set AuthenticationFailed state when biometric auth fails`() =
        runTest {
            // Given
            every { biometricAuthManager.isBiometricAvailable() } returns true
            coEvery { biometricAuthManager.authenticate() } returns BiometricAuthManager.BiometricResult.Failed

            // When
            authViewModel.publish(AuthIntent.Initialize)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val state = authViewModel.uiState.first()
            assertEquals(AuthState.AuthenticationFailed, state.authState)
        }

    @Test
    fun `publish Initialize should set AuthenticationFailed state when biometric auth errors`() =
        runTest {
            // Given
            every { biometricAuthManager.isBiometricAvailable() } returns true
            coEvery { biometricAuthManager.authenticate() } returns BiometricAuthManager.BiometricResult.Error

            // When
            authViewModel.publish(AuthIntent.Initialize)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val state = authViewModel.uiState.first()
            assertEquals(AuthState.AuthenticationFailed, state.authState)
        }

    @Test
    fun `publish Retry should perform biometric auth again and succeed`() = runTest {
        // Given
        every { biometricAuthManager.isBiometricAvailable() } returns true
        coEvery { biometricAuthManager.authenticate() } returns BiometricAuthManager.BiometricResult.Success

        // When
        authViewModel.publish(AuthIntent.Retry)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = authViewModel.uiState.first()
        assertEquals(AuthState.Authenticated, state.authState)
    }

    @Test
    fun `publish Retry should set AuthenticationFailed state when retry fails`() = runTest {
        // Given
        every { biometricAuthManager.isBiometricAvailable() } returns true
        coEvery { biometricAuthManager.authenticate() } returns BiometricAuthManager.BiometricResult.Failed

        // When
        authViewModel.publish(AuthIntent.Retry)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = authViewModel.uiState.first()
        assertEquals(AuthState.AuthenticationFailed, state.authState)
    }

    @Test
    fun `should handle NotAvailable result and set Authenticated state`() = runTest {
        // Given
        every { biometricAuthManager.isBiometricAvailable() } returns true
        coEvery { biometricAuthManager.authenticate() } returns BiometricAuthManager.BiometricResult.NotAvailable

        // When
        authViewModel.publish(AuthIntent.Initialize)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = authViewModel.uiState.first()
        assertEquals(AuthState.Authenticated, state.authState)
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        // When
        val initialState = authViewModel.uiState.first()

        // Then
        assertEquals(AuthState.Loading, initialState.authState)
    }

    @Test
    fun `publish Retry should set Loading state first then perform auth`() = runTest {
        val stateValues = mutableListOf<AuthState>()
        val stateJob = launch {
            authViewModel.uiState.collect { state ->
                stateValues.add(state.authState)
            }
        }

        // Given
        every { biometricAuthManager.isBiometricAvailable() } returns true
        coEvery { biometricAuthManager.authenticate() } returns BiometricAuthManager.BiometricResult.Success

        // When
        authViewModel.publish(AuthIntent.Retry)
        testDispatcher.scheduler.advanceTimeBy(1) // Advance just enough to see Loading state

        // Then - initially should be Loading
        val loadingState = stateValues.first()
        assertEquals(AuthState.Loading, loadingState)

        // Complete the coroutine
        testDispatcher.scheduler.advanceUntilIdle()
        val finalState = stateValues.last()
        assertEquals(AuthState.Authenticated, finalState)
        stateJob.cancel()
    }
}
