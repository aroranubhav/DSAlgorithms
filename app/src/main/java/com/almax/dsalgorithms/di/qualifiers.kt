package com.almax.dsalgorithms.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationContext

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class FoldersBaseUrl

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class FilesBaseUrl

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class ActivityContext

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class FoldersHttpClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class FilesHttpClient
