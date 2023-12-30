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
${tmp}/${pkg}/ControllerTableur.class : ${tmp}/${pkg}/VueTableur.class ${tmp}/${pkg}/SelectionListener.class
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/ControllerTableur.java src/${pkg}/ModelTableur.java src/${pkg}/CellListener.java

${tmp}/${pkg}/VueTableur.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/VueTableur.java

${tmp}/${pkg}/SelectionListener.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/SelectionListener.java

${tmp}/${pkg}/Etat.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/Etat.java

${tmp}/${pkg}/Cellule.class :
	${jvc} ${jflags} -d ${tmp} -cp ${tmp} src/${pkg}/Cellule.java

clean :
	rm -rf build/
	rm ${outfile}