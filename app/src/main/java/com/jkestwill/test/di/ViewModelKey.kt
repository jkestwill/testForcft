package com.jkestwill.test.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(allowedTargets = [AnnotationTarget.FUNCTION])
@Retention(value =  AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value : KClass<out ViewModel>)
