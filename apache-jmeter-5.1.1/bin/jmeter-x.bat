@echo off

set "XPLAT=%1"
set "XPLAN=%2"
set "YEAR=%date:~0,4%"
set "XHOME=C:\perfrunner"

if not defined XPLAT (
	call jmeter.bat
	goto end
)

if not defined XPLAN (
	call jmeter.bat
	goto end
)

set "JBINDIR=%XHOME%\apache-jmeter-5.1.1\bin"
set "XREPORT=%XHOME%\xreport\%YEAR%\%XPLAT%\%XPLAN%"
set "JSCRIPT=%JBINDIR%\anta-%YEAR%\anta_%XPLAT%.jmx"

cd %JBINDIR%
call jmeter.bat ^
 -Gxhome=%XHOME% ^
 -Gxplan=%XPLAN% ^
 -n -r -t %JSCRIPT% ^
 -e -o %XREPORT%\report\ ^
 -l %XREPORT%\log\log.jtl ^
 -j %XREPORT%\log\jmeter.log

:end
