# nido-multi-module
Importar con eclipse como  Maven > Existing Maven Projects
Configurar eclipse con la version 3.6.3 de Maven
    Windows > Maven > Installations > Add... > Directory > C:\Maven
    Marcar la nueva entrada en la tabla > Apply > Apply and Close
Configurar eclipse con la version 14 de Java
    Windows > Java > Installed JREs > Add... > Standard VM > Next > Directory... > C:\Program Files\Java\jdk-14 > Finish
    Marcar la nueva entrada en la tabla > Apply > Apply and Close


En el archivo nido-multi-module/pom.xml Comentar el interior de las etiquetas <modules>
En el modulo nido-multi-module Maven > Run As > 6 Maven Install
En el archivo nido-multi-module/pom.xml Descomentar el interior de las etiquetas <modules>
En el modulo nido-multi-module Maven > Run As > 6 Maven Install