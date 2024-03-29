## Variables
jvm = java
jvc = javac
jflags = -implicit:none
tmp = build
outfile = fr.iutfbleau.chauveau.ngwalang.thuret.excel.jar
pkg = fr/iutfbleau/chauveau/ngwalang/thuret/excel
manifest = src/manifest/manifest.txt

## Règles
run : ${outfile}
	${jvm} -jar ${outfile}

${outfile} : ${tmp}/${pkg}/Main.class
	jar -cfm ${outfile} ${manifest} -C ${tmp} .

${tmp}/${pkg}/Main.class : ${tmp}/${pkg}/ControllerTableur.class
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/Main.java

## Grosse règle circulaire ici faisant qu'il ne pouvait pas être autrement 
${tmp}/${pkg}/ControllerTableur.class : ${tmp}/${pkg}/VueTableur.class ${tmp}/${pkg}/SelectionListener.class ${tmp}/${pkg}/Noeud.class ${tmp}/${pkg}/Etat.class ${tmp}/${pkg}/CellObserver.class
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/ControllerTableur.java src/${pkg}/ModelTableur.java src/${pkg}/CellListener.java src/${pkg}/ArbreBinaire.java src/${pkg}/Cellule.java src/${pkg}/ReferenceCirculaire.java

${tmp}/${pkg}/VueTableur.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/VueTableur.java

${tmp}/${pkg}/SelectionListener.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/SelectionListener.java

${tmp}/${pkg}/Etat.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/Etat.java


${tmp}/${pkg}/Noeud.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/Noeud.java

${tmp}/${pkg}/CellObserver.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/CellObserver.java
		

clean :
	rm -rf ${tmp}
	rm ${outfile}