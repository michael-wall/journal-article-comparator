## Introduction ##
- This module contains 2 custom comparator classes for Journal Article that expose a default constructor to be compatible with Journal Article JSONWS +orderByComparator request parameter syntax:
	- com.mw.comparator.ArticleCreateDateAscComparator
	- com.mw.comparator.ArticleCreateDateDescComparator
- The absence of a default constructor in the OOTB Journal Article comparators causes the following exception when one is used in this scenario:
```
2025-12-10 17:12:08.020 DEBUG [http-nio-8080-exec-10][JSONWebServiceServiceAction:120] com.liferay.journal.util.comparator.ArticleDisplayDateComparator
java.lang.InstantiationException: com.liferay.journal.util.comparator.ArticleDisplayDateComparator
        at java.lang.Class.newInstance(Class.java:719)
...
...
...
Caused by: java.lang.NoSuchMethodException: com.liferay.journal.util.comparator.ArticleDisplayDateComparator.<init>()
        at java.lang.Class.getConstructor0(Class.java:3761)
        at java.lang.Class.newInstance(Class.java:706)
```
- It also has Fragment-Host: com.liferay.journal.service set in bnd.bnd to attach it to the journal service to resolve class path issues.
- To use with JSONWS endpoints the classes MUST be whitelisted in the jsonws.web.service.parameter.type.whitelist.class.names portal property.
  - Copy the OOTB property value from tomcat\webapps\ROOT\WEB-INF\shielded-container-lib\portal-impl.jar > portal.properties.
  - Append the class names using the correct syntax

## Known Issue (under investigation with Product team) ##
- While the custom comparators work when set using the JSONWS Explorer GUI but they don't work using curl syntax such as:
```
curl -u "test@liferay.com:password" "http://localhost:8080/api/jsonws/journal.journalarticle/get-articles-by-structure-id/group-id/20118/ddm-structure-id/33717/start/0/end/10/+order-by-comparator/com.mw.comparator.ArticleCreateDateAscComparator"
```
or
```
curl http://localhost:8080/api/jsonws/journal.journalarticle/get-articles-by-structure-id \
-u test@liferay.com:test \
-d groupId=20118 \
-d ddmStructureId=33717 \
-d start=0 \
-d end=10 \
-d +orderByComparator=com.mw.comparator.ArticleCreateDateAscComparator
```
- These result in the following error:
```
2025-12-10 20:15:41.478 DEBUG [http-nio-8080-exec-1][JSONWebServiceServiceAction:120] No JSON web service action with path /journal.journalarticle/get-articles-by-structure-id and method GET for journal
```
- This issue isn't specific to the custom comparators, it occurs when then any request parameter value is used for the +orderByComparator request parameter for Journal Article endpoints using this syntax.
- The syntax is correct as the following works for Audit Events:
```
curl -u "test@liferay.com:password" "http://localhost:8080/api/jsonws/audit.auditevent/get-audit-events/company-id/22590512522764/start/0/end/10/+order-by-comparator/com.liferay.portal.security.audit.storage.comparator.AuditEventCreateDateComparator"
```

## Notes ##
- This is a ‘proof of concept’ that is being provided ‘as is’ without any support coverage or warranty.
- It was created for 2025.Q1.18
