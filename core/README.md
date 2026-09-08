# README
This folder contains all Java core topics:

## Jar
This section contains everything about jar.


To make jar file: `$ jar -cvf <a> [<b>] <c>`  
`a`: arbitrary name for your jar file  
`b`: OPTIONAL - fully qualified name of the class containing main() ==> for executable jar ONLY  
`c`: list of all .class files to be included in the jar; separated by ' ' (space)

To compile with 3rd party jar included in our project: `$ javac -cp <a> <b>`  
`a`: location of .class file of your own project + jar files, separated by ; (Windows) or : (Linux). NOTE: For Windows, enclose this in quotes "".  
`b`: java files to be compiled

### Example 1 - Create a Single Jar File Containing Single Class File 
Our external 3rd party lib project structure is like below:  
![](img/external_lib.png)  


#### Steps
***NOTE***: `$` is a short form of `...\external\src $` as current directory  
1. Compile: `$ javac -d bin com\blabla\MainBla.java` ==> this create *MainBla.class* in *bin* folder
2. Change directory to `bin` where `com.blabla.Main.class` is located: `$ cd bin`
3. Create jar: `..\bin $ jar -cvf blablalib.jar com\blabla\MainBla.class`
4. Check jar contents: `..\bin $ jar -tf blablalib.jar`:
    ```
    META-INF/
    META-INF/MANIFEST.MF
    com/blabla/MainBla.class
    ```
### Example 2 - Create a Multiple Jar Files Containing Multiple Class Files
Our external 3rd party lib project structure is like below:  
![external_multiple_jars](img/external_multiple_jars.png)  
*NOTE*: `bin` folder is only created in step 1 below containing class files
#### Steps
***NOTE***: `$` is a short form of `...\external\src $` for current directory
1. Compile: `$ javac -d bin com\math1\*.java com\math2\*.java` ==> this create *.class* files in *bin* folder
2. Change directory to `bin` where compiled class files from step 1 (`com.math1.Adder.class`, etc) are located: `$ cd bin`
3. Create jars:  
   a. `..\bin $ jar -cvf math1.jar com\math1\*.class`  
   b. `..\bin $ jar -cvf math2.jar com\math2\*.class`
4. Check jars contents:  
   a. `..\bin $ jar -tf math1.jar`:
   ```
   META-INF/
   META-INF/MANIFEST.MF
   com/math1/Adder.class
   com/math1/Subtractor.class
   ```
   b. `..\bin $ jar -tf math2.jar`:  
   ```
   META-INF/
   META-INF/MANIFEST.MF
   com/math2/Divider.class
   com/math2/Multiplier.class
   ```

### Example 3 - Include a Jar File Into Our Project
Example:  
We have a project called rod_jar_test with structure below. The 3rd party jars are stored in *lib* folder.  
![project_structure.png](img/project_structure.png)

Code: 
```
package com.rtaylor02;

import com.blabla.MainBla;

public class Main {
    public static void main(String[] args) {
        MainBla mainBla = new MainBla();
        System.out.println("Result: " + mainBla.add1(2));
    }
}
```

#### Steps
***NOTE***: `$` is a short form of `...\rod_jar_test $` as current directory
1. Copy and paste the lib jar(s) to *lib* folder
2. Compile our project: `$ javac -d target -cp lib\blablalib.jar src\com\rtaylor02\Main.java` ==> this create *Main.class* in *target* folder
3. Execute our project: `$ java -cp "target;lib\blablalib.jar" com.rtaylor02.Main`

Result:
```
Result: 3
```
### Example 4 - Include Multiple Jar Files Into Our Project
Example:  
We have a project called rod_jar_test with structure below. The 3rd party jars are stored in *lib* folder.  
![project_structure_multiple_jars](img/project_structure_multiple_jars.png)  
*NOTE*: `target` folder is only created after step 2 below.

Code:
```
package com.rtaylor02;

import com.math1.Adder;
import com.math1.Subtractor;
import com.math2.Divider;
import com.math2.Multiplier; 

public class Main {
    public static void main(String[] args) {
        Adder adder = new Adder();
        System.out.println("add(1, 2) = " + adder.add(1, 2));

        Subtractor subtractor = new Subtractor();
        System.out.println("subtract(2, 1) = " + subtractor.subtract(2, 1));

        Divider divider = new Divider();
        System.out.println("divide(6, 2) = " + divider.divide(6, 2));

        Multiplier multiplier = new Multiplier();
        System.out.println("multiply(2, 2) = " + multiplier.multiply(2, 2));
    }
}
```

#### Steps
***NOTE***: `$` is a short form of `...\rod_jar_test $` as current directory
1. Copy and paste the lib jar(s) to *lib* folder
2. Compile our project: `$ javac -d target -cp "lib\*" src\com\rtaylor02\Main.java` ==> this create *Main.class* in *target* folder.
   > NOTE: for `classpath`, you can specify individual jars, or use wildcard *. DO NOT use *.jar!
3. Execute our project: `$ java -cp "lib\*;target" com.rtaylor02.Main`.

Result:
```
add(1, 2) = 3
subtract(2, 1) = 1
divide(6, 2) = 3
multiply(2, 2) = 4
```
![image](https://github.com/user-attachments/assets/1eb3e2be-0090-451c-9cb4-1eb76b983cd7)

### Example 5 - Create a distributable & executable jar consisting all 3rd party libraries and your own jar containing main class
Example:  
We have a project called rod_fat_jar structure below. The 3rd party jars are stored in *lib* folder.  
![structure before compilation](img/fat_jar_pre_compilation.png)  

Code:
```
package com.rtaylor02;

import com.math1.Adder;
import com.math1.Subtractor;
import com.math2.Divider;
import com.math2.Multiplier; 

public class Main {
    public static void main(String[] args) {
        Adder adder = new Adder();
        System.out.println("add(1, 2) = " + adder.add(1, 2));

        Subtractor subtractor = new Subtractor();
        System.out.println("subtract(2, 1) = " + subtractor.subtract(2, 1));

        Divider divider = new Divider();
        System.out.println("divide(6, 2) = " + divider.divide(6, 2));

        Multiplier multiplier = new Multiplier();
        System.out.println("multiply(2, 2) = " + multiplier.multiply(2, 2));
    }
}
```

#### Steps
***NOTE***: `$` is a short form of `...\rod_fat_jar $` as current directory
1. Copy and paste the lib jar(s) to *lib* folder
2. Compile our project: `$ javac -d out -cp "lib\*" src\com\rtaylor02\Main.java` ==> this create *Main.class* in *out* folder.
   > NOTE: for `classpath`, you can specify individual jars, or use wildcard *. DO NOT use *.jar!  
   > 
   ![Compilation result](img/fat_jar_post_compilation.png)
3. Execute our project to test all is running well: `$ java -cp "lib\*;target" com.rtaylor02.Main`.
   ![Successful run](img/fat_jar_run_test.png)
4. Distribution steps:
   1) Extract dependencies into *out* folder:  
   `$ cd out`  
   `$ jar xf ..\lib\math1.jar`  
   `$ jar xf ..\lib\math2.jar`  
   ![extract dependencies](img/fat_jar_extract_dependencies.png)
   2) Create a jar file:  
   `$ jar cfe ..\rod.jar com.rtaylor02.Main .`  
   ![Create a fat jar file](img/fat_jar_create_jar.png)  
   > NOTE: fat jar file contains all the dependencies `.class` files, as opposed to `.jar` files.  
   3) Test jar file:  
   `$ cd ..`  
   `$ java -jar rod.jar`  
   ![Final result](img/fat_jar_final_result.png)  



