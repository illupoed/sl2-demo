Diese Vorlage wurde getestet auf einer 2.9 Miktex-Installation. 

Als Editor wird TexnicCenter oder eclipse (Plugin: texlipse) empfohlen.

CM-Super Fonts müssen installiert sein. Ghostscript muss installiert sein.

Optischer Randausgleich wird erreicht durch Nutzung von pdfTex. 
Für die Verwendung von eps-Grafiken wird eine dvi-Datei erzeugt und anschließend
in ein pdf konvertiert, indem "latex->PS->pdf" ausgewählt wird. Ein entsprechendes
Ausgabeprofil für TeXnicCenter ist unter "\Profile" vorhanden.
Bei diesem müssen ggf. die Pfade zu den 
verwendeten Anwendungen (latex.exe, dvi2ps.exe, ps2pdf(gswin32c.exe), Acrobat Reader) angepaßt werden.


In dem Verzeichnis sind Projektdateien für:

1.) TexnicCenter

TemplateStudDipl.tcp
TemplateStudDipl.tps
TexnicCenterProfil_latex_ps_pdf.tco (unter "\Profile")

2.) Eclipse

.project
.texlipse

vorhanden.
Bei diesem müssen ggf. die Pfade zu den 
verwendeten Anwendungen (dvi2ps, ps2pdf, Acrobat Reader) angepaßt werden.
