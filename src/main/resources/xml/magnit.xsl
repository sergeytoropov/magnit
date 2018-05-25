<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns="http://ru.sergeytoropov.magnit/xml/2"
        xmlns:st1="http://ru.sergeytoropov.magnit/xml/1"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        version="2.0">

    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="st1:entries">
        <xsl:element name="entries">
            <xsl:for-each select="st1:entry">
                <xsl:element name="entry">
                    <xsl:attribute name="field">
                        <xsl:value-of select="./st1:field/text()"/>
                    </xsl:attribute>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
