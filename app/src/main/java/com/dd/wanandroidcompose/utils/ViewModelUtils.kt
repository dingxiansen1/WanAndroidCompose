package com.dd.wanandroidcompose.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
inline fun <reified T : ViewModel> viewModelInstance(crossinline create:()-> T):T{
    return viewModel(factory = object : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return create() as T
        }
    }) as T
}
