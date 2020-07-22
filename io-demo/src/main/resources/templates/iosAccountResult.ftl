<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <title></title>
    <style type="text/css">
        body {
            font-family: pingfang sc light;
        }
        .center{
            text-align: center;
            width: 100%;
        }
        .dotted {border-style: dotted}
        .dashed {border-style: dashed}
        table td {
            border-style: solid
        }
        .double {border-style: double}
        .groove {border-style: groove}
        .ridge {border-style: ridge}
        .inset {border-style: inset}
        .outset {border-style: outset}



    </style>
</head>
<body>

<!--第一页开始  数据报表 + 首张截图 -->
<div class="page" >
    <div>
        <table width="100%" cellspacing=0 cellpadding=0 border="1" bordercolor=#000000 cellpadding="5">
            <tr>
                <td colspan="4" style="text-align:center;font-size: 20px;">${name}</td>
            </tr>
            <tr>
                <td colspan="3">当地时间：${date}</td>
                <td colspan="1">币种：${currency}</td>
            </tr>
            <tr>
                <td style="text-align:center;">游戏</td>
                <td style="text-align:center;">收入(US$)</td>
                <td style="text-align:center;">成本(US$)</td>
                <td style="text-align:center;">净收入(US$)</td>
            </tr>
                        <#list resultResList as item>
                            <tr>
                                <td style="text-align:left;">${item.gameCnName}</td>
                                <td style="text-align:right;">${item.income}</td>
                                <td style="text-align:right;">${item.cost}</td>
                                <td style="text-align:right;">${item.dollar}</td>
                            </tr>
                        </#list>

            <tr>
                <td style="text-align:left;">Total</td>
                <td style="text-align:right;">${totalRes.income}</td>
                <td style="text-align:right;">${totalRes.cost}</td>
                <td style="text-align:right;">${totalRes.dollar}</td>
            </tr>
        </table>
    </div>

    <br/>
    <br/>

    <#if imageUrls?? && (imageUrls?size > 0) >
        <div >
            <table width="100%" cellspacing=0 cellpadding=0 border="0" bordercolor=#000000 cellpadding="5">
                <tr>
                    <td colspan="4" style="text-align:center;font-size: 20px;">
                        <img src="${imageUrls[0]}" alt="111" width="650" height="500" style=""/>
                    </td>
                </tr>
            </table>
        </div>
    </#if>

</div>

<span style="page-break-after:always;"></span>


<!--如果有多张截图，两张做一页-->

<#if imageUrls?? && (imageUrls?size > 1) >

    <#list imageUrls as imageUrl>

        <#if imageUrl_index gt 0>

            <#if imageUrl_index % 2 = 1 > <#-- -->
                <div class="page" >
            </#if>
                <div>
                    <table width="100%" cellspacing=0 cellpadding=0 border="0" bordercolor=#000000 cellpadding="5">
                        <tr>
                            <td colspan="4" style="text-align:center;font-size: 20px;">
                                <img src="${imageUrl}" alt="111" width="650" height="500" style=""/>
                            </td>
                        </tr>
                    </table>
                </div>
            <#if imageUrl_index % 2 = 0 >
                </div>
                <span style="page-break-after:always;"></span>
            </#if>

        </#if>

    </#list>

    <#if imageUrls?size % 2 = 0 >
        </div>
        <span style="page-break-after:always;"></span>
    </#if>

</#if>

</body>
</html>