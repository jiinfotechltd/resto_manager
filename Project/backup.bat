@echo off

:: MySQL credentials
SET DB_USER=root
SET DB_PASSWORD=root
SET DB_NAME=resto_manager

:: Backup location (Windows path)
SET BACKUP_PATH=C:\backups\mysql
SET DATE=%DATE:/=-%_%TIME::=-%

:: Create backup directory if it doesn't exist
if not exist "%BACKUP_PATH%" (
    mkdir "%BACKUP_PATH%"
)

:: Run mysqldump (ensure correct path to mysqldump)
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqldump.exe" -u%DB_USER% -p%DB_PASSWORD% %DB_NAME% > "%BACKUP_PATH%\%DB_NAME%-%DATE%.sql"

:: Check if the backup was successful
if %ERRORLEVEL% == 0 (
    echo Backup completed successfully for database %DB_NAME% at %DATE%.
) else (
    echo Backup failed for database %DB_NAME% at %DATE%.
    exit /b 1
)

:: Assuming a cloud storage upload is triggered here or move the file to the desired location
:: Deleting the backup file after upload
:: del "%BACKUP_PATH%\%DB_NAME%-%DATE%.sql"
