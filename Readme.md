# Vigenere Coder

This app is a simple password manager. It uses Vegenere's cipher to encrypt your passwords and save it into SQLite DB.

### How to use it

The simplest way to use it is open .jar file. It needs at least java 16.  
App will create an empty s3db file in your home folder or will use an exists one.
If you want to transfer your passwords - copy .s3db file from your home directory.  
It also supports console mode - if you give `-C` as an argument.

### Docker 

You can use a docker image to launch this application with following command:
```
docker run -it -v PATH_TO_YOUR_FILE.s3db:/app/.s3db pashabezborod/vigenere-coder
```
It runs only in console mode with Docker of course.

### Caution

Be careful! If you enter app with a wrong cipher and then change it - you'll lose your passwords.  
This app is made mostly for practice and having fun. If you really care about your passwords safety - use it on your own risk