grails.plugin.springsecurity.securityConfigType = "Annotation"
grails.plugin.springsecurity.rejectIfNoRule = true
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/'

grails.plugin.springsecurity.userLookup.userDomainClassName = 'myapp.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'myapp.UserRole'
grails.plugin.springsecurity.authority.className = 'myapp.Role'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/',                                  access: ['permitAll']],
        [pattern: '/test/**',                           access: ['permitAll']],
        [pattern: '/error',                             access: ['permitAll']],
        [pattern: '/index',                             access: ['permitAll']],
        [pattern: '/index.gsp',                         access: ['permitAll']],
        [pattern: '/shutdown',                          access: ['permitAll']],
        [pattern: '/assets/**',                         access: ['permitAll']],
        [pattern: '/**/js/**',                          access: ['permitAll']],
        [pattern: '/**/css/**',                         access: ['permitAll']],
        [pattern: '/**/images/**',                      access: ['permitAll']],
        [pattern: '/**/favicon.ico',                    access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**',      filters: 'none'],
        [pattern: '/**/js/**',       filters: 'none'],
        [pattern: '/**/css/**',      filters: 'none'],
        [pattern: '/**/images/**',   filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**',             filters: 'JOINED_FILTERS']
]



// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/',               access: ['permitAll']],
        [pattern: '/error',          access: ['permitAll']],
        [pattern: '/index',          access: ['permitAll']],
        [pattern: '/index.gsp',      access: ['permitAll']],
        [pattern: '/shutdown',       access: ['permitAll']],
        [pattern: '/assets/**',      access: ['permitAll']],
        [pattern: '/**/js/**',       access: ['permitAll']],
        [pattern: '/**/css/**',      access: ['permitAll']],
        [pattern: '/**/images/**',   access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**',      filters: 'none'],
        [pattern: '/**/js/**',       filters: 'none'],
        [pattern: '/**/css/**',      filters: 'none'],
        [pattern: '/**/images/**',   filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**',             filters: 'JOINED_FILTERS']
]