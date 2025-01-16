@echo off

SET DB_USER=root
SET DB_PASSWORD=root
SET DB_NAME=resto_manager

SET BACKUP_PATH=C:\backups\mysql
SET DATE=%DATE:/=-%_%TIME::=-%

"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqldump.exe" -u%DB_USER% -p%DB_PASSWORD% %DB_NAME% > "%BACKUP_PATH%\%DB_NAME%-%DATE%.sql"

if %ERRORLEVEL% == 0 (
    echo Backup completed successfully for database %DB_NAME% at %DATE%.
) else (
    echo Backup failed for database %DB_NAME% at %DATE%.
    exit /b 1
)