# grails-nondefault-datasource-lazy-loading-fail
The purpose of this repository is to report grails lazy loading failure when entities are mapped to a non default datasource

### Description
we have two domain entities "Author" and "Book", they have bi-directional One-to-Many relationship.

we have two data sources defined in "Configuration/application.yml" that are "dataSource" and "dataSource2".

when "Author" and "Book" domain entities are mapped to "dataSource2" we get lazy loading error,

but when same entities are mapped to default "dataSource" we do not get lazy loading error - also for that we do no need to have "Initialization/myapp/PersistenceConfig" configuration file that sets-up "GrailsOpenSessionInViewInterceptor" 

it seems like lazy loading does not work with non-default data sources.

## Current Setup

1. we are using H2 in-memory database
2. there are two data sources defined, one is default 'dataSource' and another one is "dataSource2"
3. we have defined "Initialization/myapp/PersistenceConfig" configuration file that creates "GrailsOpenSessionInViewInterceptor" for non default data source "dataSource2"
4. Login using username: admin and password: admin
4. Currently "Author" and "Book" both entities are mapped to default data source (because no datasource is defined on the entities)
5. TestController loads "Author" entity using "createCriteria"."list(){}" and associated view "index.gsp" iterates over the list of "Author" and associated "Book"
6. This current setup works as per expectations using Lazy loading

## Steps to Reproduce
1. To reproduce simple uncomment "static mapping" in "Author" and "Book" entities
2. this will map both the entities to non-default data source that is "dataSource2"
3. Run application using grails run-app or ./grailsw run-app (grant execute permission to grailsw), use admin/admin as loing credentials and execute "TestController" where list of "Author" entity is queried and passed to the corresponding gsp that "Views/test/index.gsp"
3. when index.gsp iterates over the list of "Author" and associated list of "Book" it throws "PooledConnection has already been closed"

Note: 
1. If Configuration file "Initialization/PersistenceConfig" that sets-up "GrailsOpenSessionInViewInterceptor" for data source "dataSource2" is not defined then we get
"failed to lazily initialize a collection of role: myapp.Author.books, could not initialize proxy - no Session"

2. if we iterate over the Author's list of books in the controller which is marked "@@Transactional" this populated the Author's books but it is not accepted
because we want this to work in gsp to lazily load associated entities using OSIV.

	
## Expected Behavior
1. it should not matter what data source is defined for the entities whether default or non-default, lazy loading should work.


## Stacktrace - when "Initialization/myapp/PersistenceConfig" Configuration is present
2018-11-13 10:55:46.005  WARN --- [nio-8080-exec-6] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: null
2018-11-13 10:55:46.005 ERROR --- [nio-8080-exec-6] o.h.engine.jdbc.spi.SqlExceptionHelper   : PooledConnection has already been closed.
2018-11-13 10:55:46.008 ERROR --- [nio-8080-exec-6] .a.c.c.C.[.[.[.[grailsDispatcherServlet] : Servlet.service() for servlet [grailsDispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.grails.gsp.GroovyPagesException: Error processing GroovyPageView: could not prepare statement] with root cause

java.sql.SQLException: PooledConnection has already been closed.
	at org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy$LazyConnectionInvocationHandler.invoke(LazyConnectionDataSourceProxy.java:376)
	at org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy$TransactionAwareInvocationHandler.invoke(TransactionAwareDataSourceProxy.java:240)
	at org.hibernate.engine.jdbc.internal.StatementPreparerImpl$5.doPrepare(StatementPreparerImpl.java:146)
	at org.hibernate.engine.jdbc.internal.StatementPreparerImpl$StatementPreparationTemplate.prepareStatement(StatementPreparerImpl.java:172)
	at org.hibernate.engine.jdbc.internal.StatementPreparerImpl.prepareQueryStatement(StatementPreparerImpl.java:148)
	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.prepareQueryStatement(AbstractLoadPlanBasedLoader.java:241)
	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeQueryStatement(AbstractLoadPlanBasedLoader.java:185)
	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeLoad(AbstractLoadPlanBasedLoader.java:121)
	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeLoad(AbstractLoadPlanBasedLoader.java:86)
	at org.hibernate.loader.collection.plan.AbstractLoadPlanBasedCollectionInitializer.initialize(AbstractLoadPlanBasedCollectionInitializer.java:88)
	at org.hibernate.persister.collection.AbstractCollectionPersister.initialize(AbstractCollectionPersister.java:688)
	at org.hibernate.event.internal.DefaultInitializeCollectionEventListener.onInitializeCollection(DefaultInitializeCollectionEventListener.java:75)
	at org.hibernate.internal.SessionImpl.initializeCollection(SessionImpl.java:2004)
	at org.hibernate.collection.internal.AbstractPersistentCollection$4.doWork(AbstractPersistentCollection.java:567)
	at org.hibernate.collection.internal.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:249)
	at org.hibernate.collection.internal.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:563)
	at org.hibernate.collection.internal.AbstractPersistentCollection.read(AbstractPersistentCollection.java:132)
	at org.hibernate.collection.internal.PersistentSet.iterator(PersistentSet.java:163)
	at tmp_grails_nondefault_datasource_lazy_loading_fail_grails_app_views_test_index_gsp$_run_closure2.doCall(tmp_grails_nondefault_datasource_lazy_loading_fail_grails_app_views_test_index_gsp:34)
	at org.grails.taglib.TagBodyClosure.executeClosure(TagBodyClosure.java:200)
	at org.grails.taglib.TagBodyClosure.captureClosureOutput(TagBodyClosure.java:102)
	at org.grails.taglib.TagBodyClosure.call(TagBodyClosure.java:213)
	at org.grails.plugins.web.taglib.SitemeshTagLib.captureTagContent(SitemeshTagLib.groovy:48)
	at org.grails.plugins.web.taglib.SitemeshTagLib$_closure3.doCall(SitemeshTagLib.groovy:156)
	at org.grails.gsp.GroovyPage.invokeTagLibClosure(GroovyPage.java:446)
	at org.grails.gsp.GroovyPage.invokeTag(GroovyPage.java:364)
	at tmp_grails_nondefault_datasource_lazy_loading_fail_grails_app_views_test_index_gsp.run(tmp_grails_nondefault_datasource_lazy_loading_fail_grails_app_views_test_index_gsp:43)
	at org.grails.gsp.GroovyPageWritable.doWriteTo(GroovyPageWritable.java:162)
	at org.grails.gsp.GroovyPageWritable.writeTo(GroovyPageWritable.java:82)
	at org.grails.web.servlet.view.GroovyPageView.renderTemplate(GroovyPageView.java:76)
	at org.grails.web.servlet.view.AbstractGrailsView.renderWithinGrailsWebRequest(AbstractGrailsView.java:71)
	at org.grails.web.servlet.view.AbstractGrailsView.renderMergedOutputModel(AbstractGrailsView.java:55)
	at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:303)
	at org.grails.web.sitemesh.GrailsLayoutView.renderInnerView(GrailsLayoutView.java:150)
	at org.grails.web.sitemesh.GrailsLayoutView.obtainContent(GrailsLayoutView.java:128)
	at org.grails.web.sitemesh.GrailsLayoutView.renderTemplate(GrailsLayoutView.java:63)
	at org.grails.web.servlet.view.AbstractGrailsView.renderWithinGrailsWebRequest(AbstractGrailsView.java:71)
	at org.grails.web.servlet.view.AbstractGrailsView.renderMergedOutputModel(AbstractGrailsView.java:55)
	at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:303)
	at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1286)
	at org.springframework.web.servlet.DispatcherServlet.processDispatchResult(DispatcherServlet.java:1041)
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:984)
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:901)
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:970)
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:861)
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:846)
	at org.springframework.boot.web.filter.ApplicationContextHeaderFilter.doFilterInternal(ApplicationContextHeaderFilter.java:55)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:317)
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:127)
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:91)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:114)
	at grails.plugin.springsecurity.web.UpdateRequestContextHolderExceptionTranslationFilter.doFilter(UpdateRequestContextHolderExceptionTranslationFilter.groovy:64)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at grails.plugin.springsecurity.web.filter.GrailsAnonymousAuthenticationFilter.doFilter(GrailsAnonymousAuthenticationFilter.groovy:54)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter.doFilter(RememberMeAuthenticationFilter.java:158)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:170)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:200)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at grails.plugin.springsecurity.web.authentication.logout.MutableLogoutFilter.doFilter(MutableLogoutFilter.groovy:64)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:105)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at grails.plugin.springsecurity.web.SecurityRequestHolderFilter.doFilter(SecurityRequestHolderFilter.groovy:58)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:214)
	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:177)
	at org.grails.web.servlet.mvc.GrailsWebRequestFilter.doFilterInternal(GrailsWebRequestFilter.java:77)
	at org.grails.web.filters.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:67)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)


## Stacktrace - when "Initialization/myapp/PersistenceConfig" Configuration is not present and entities are mapped to non-default data source
2018-11-13 11:32:13.972 ERROR --- [nio-8080-exec-3] .a.c.c.C.[.[.[.[grailsDispatcherServlet] : Servlet.service() for servlet [grailsDispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.grails.gsp.GroovyPagesException: Error processing GroovyPageView: failed to lazily initialize a collection of role: myapp.Author.books, could not initialize proxy - no Session] with root cause

org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: myapp.Author.books, could not initialize proxy - no Session
	at org.hibernate.collection.internal.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:584)
	at org.hibernate.collection.internal.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:201)
	at org.hibernate.collection.internal.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:563)
	at org.hibernate.collection.internal.AbstractPersistentCollection.read(AbstractPersistentCollection.java:132)
	at org.hibernate.collection.internal.PersistentSet.iterator(PersistentSet.java:163)
	at tmp_grails_nondefault_datasource_lazy_loading_fail_grails_app_views_test_index_gsp$_run_closure2.doCall(tmp_grails_nondefault_datasource_lazy_loading_fail_grails_app_views_test_index_gsp:34)
	at org.grails.taglib.TagBodyClosure.executeClosure(TagBodyClosure.java:200)
	at org.grails.taglib.TagBodyClosure.captureClosureOutput(TagBodyClosure.java:102)
	at org.grails.taglib.TagBodyClosure.call(TagBodyClosure.java:213)
	at org.grails.plugins.web.taglib.SitemeshTagLib.captureTagContent(SitemeshTagLib.groovy:48)
	at org.grails.plugins.web.taglib.SitemeshTagLib$_closure3.doCall(SitemeshTagLib.groovy:156)
	at org.grails.gsp.GroovyPage.invokeTagLibClosure(GroovyPage.java:446)
	at org.grails.gsp.GroovyPage.invokeTag(GroovyPage.java:364)
	at tmp_grails_nondefault_datasource_lazy_loading_fail_grails_app_views_test_index_gsp.run(tmp_grails_nondefault_datasource_lazy_loading_fail_grails_app_views_test_index_gsp:43)
	at org.grails.gsp.GroovyPageWritable.doWriteTo(GroovyPageWritable.java:162)
	at org.grails.gsp.GroovyPageWritable.writeTo(GroovyPageWritable.java:82)
	at org.grails.web.servlet.view.GroovyPageView.renderTemplate(GroovyPageView.java:76)
	at org.grails.web.servlet.view.AbstractGrailsView.renderWithinGrailsWebRequest(AbstractGrailsView.java:71)
	at org.grails.web.servlet.view.AbstractGrailsView.renderMergedOutputModel(AbstractGrailsView.java:55)
	at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:303)
	at org.grails.web.sitemesh.GrailsLayoutView.renderInnerView(GrailsLayoutView.java:150)
	at org.grails.web.sitemesh.GrailsLayoutView.obtainContent(GrailsLayoutView.java:128)
	at org.grails.web.sitemesh.GrailsLayoutView.renderTemplate(GrailsLayoutView.java:63)
	at org.grails.web.servlet.view.AbstractGrailsView.renderWithinGrailsWebRequest(AbstractGrailsView.java:71)
	at org.grails.web.servlet.view.AbstractGrailsView.renderMergedOutputModel(AbstractGrailsView.java:55)
	at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:303)
	at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1286)
	at org.springframework.web.servlet.DispatcherServlet.processDispatchResult(DispatcherServlet.java:1041)
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:984)
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:901)
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:970)
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:861)
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:846)
	at org.springframework.boot.web.filter.ApplicationContextHeaderFilter.doFilterInternal(ApplicationContextHeaderFilter.java:55)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:317)
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:127)
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:91)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:114)
	at grails.plugin.springsecurity.web.UpdateRequestContextHolderExceptionTranslationFilter.doFilter(UpdateRequestContextHolderExceptionTranslationFilter.groovy:64)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at grails.plugin.springsecurity.web.filter.GrailsAnonymousAuthenticationFilter.doFilter(GrailsAnonymousAuthenticationFilter.groovy:54)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter.doFilter(RememberMeAuthenticationFilter.java:158)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:170)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:200)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at grails.plugin.springsecurity.web.authentication.logout.MutableLogoutFilter.doFilter(MutableLogoutFilter.groovy:64)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:105)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at grails.plugin.springsecurity.web.SecurityRequestHolderFilter.doFilter(SecurityRequestHolderFilter.groovy:58)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:331)
	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:214)
	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:177)
	at org.grails.web.servlet.mvc.GrailsWebRequestFilter.doFilterInternal(GrailsWebRequestFilter.java:77)
	at org.grails.web.filters.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:67)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
