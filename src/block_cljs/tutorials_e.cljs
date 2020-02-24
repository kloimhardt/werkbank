(ns block-cljs.tutorials-e)

(def vect
  [
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"9;bQZa1%+|JCx*4hh}!e\">println</variable>\n    <variable id=\"fmT%D,Q5*{m-a+m7qsID\">inc</variable>\n    <variable id=\"vmDJ3TaMN3q{*o,bCXkd\">@app-state</variable>\n  </variables>\n  <block type=\"funs-h-2-cus\" id=\"Z~FxXj/f),[r%GlF{(3K\" x=\"17\" y=\"30\">\n    <field name=\"kopf\" id=\"9;bQZa1%+|JCx*4hh}!e\">println</field>\n  </block>\n  <block type=\"variables_get\" id=\"2UauHNvS;h).8qDT4Q(8\" x=\"189\" y=\"42\">\n    <field name=\"VAR\" id=\"vmDJ3TaMN3q{*o,bCXkd\">@app-state</field>\n  </block>\n  <block type=\"funs-h-2-cus\" id=\"Q|`fzC5LUvE7Y!Cq%a;?\" x=\"21\" y=\"105\">\n    <field name=\"kopf\" id=\"fmT%D,Q5*{m-a+m7qsID\">inc</field>\n  </block>\n  <block type=\"variables_get\" id=\"}U3*djk^SD(,@siQ7Zjg\" x=\"235\" y=\"118\">\n    <field name=\"VAR\" id=\"vmDJ3TaMN3q{*o,bCXkd\">@app-state</field>\n  </block>\n  <block type=\"funs-h-2-cus\" id=\"pTp1#I;OA7DwN]3nxTxD\" x=\"19\" y=\"179\">\n    <field name=\"kopf\" id=\"9;bQZa1%+|JCx*4hh}!e\">println</field>\n  </block>\n  <block type=\"variables_get\" id=\"~3mVjr4:UvAruPA(SE!K\" x=\"168\" y=\"189\">\n    <field name=\"VAR\" id=\"vmDJ3TaMN3q{*o,bCXkd\">@app-state</field>\n  </block>\n</xml>"
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"9;bQZa1%+|JCx*4hh}!e\">println</variable>\n    <variable id=\":u7z7;)e6:Nw+00c{Qhg\">swap!</variable>\n    <variable id=\"z*HH-9ZbK~oxy2]!)HJ5\">app-state</variable>\n    <variable id=\"fmT%D,Q5*{m-a+m7qsID\">inc</variable>\n    <variable id=\"vmDJ3TaMN3q{*o,bCXkd\">@app-state</variable>\n  </variables>\n  <block type=\"funs-h-2-cus\" id=\"Z~FxXj/f),[r%GlF{(3K\" x=\"8\" y=\"32\">\n    <field name=\"kopf\" id=\"9;bQZa1%+|JCx*4hh}!e\">println</field>\n  </block>\n  <block type=\"variables_get\" id=\"u7cW)RILRLtn13)s.N:G\" x=\"231\" y=\"46\">\n    <field name=\"VAR\" id=\"z*HH-9ZbK~oxy2]!)HJ5\">app-state</field>\n  </block>\n  <block type=\"funs-h-3-cus\" id=\"_#Y.AL7R/SL|8Hol81?8\" x=\"12\" y=\"108\">\n    <field name=\"kopf\" id=\":u7z7;)e6:Nw+00c{Qhg\">swap!</field>\n  </block>\n  <block type=\"variables_get\" id=\"*muHCX*uCjD)G9%6bexh\" x=\"190\" y=\"108\">\n    <field name=\"VAR\" id=\"fmT%D,Q5*{m-a+m7qsID\">inc</field>\n  </block>\n  <block type=\"variables_get\" id=\"2UauHNvS;h).8qDT4Q(8\" x=\"297\" y=\"109\">\n    <field name=\"VAR\" id=\"vmDJ3TaMN3q{*o,bCXkd\">@app-state</field>\n  </block>\n  <block type=\"funs-h-2-cus\" id=\"pTp1#I;OA7DwN]3nxTxD\" x=\"9\" y=\"181\">\n    <field name=\"kopf\" id=\"9;bQZa1%+|JCx*4hh}!e\">println</field>\n    <value name=\"args-2\">\n      <block type=\"variables_get\" id=\"~3mVjr4:UvAruPA(SE!K\">\n        <field name=\"VAR\" id=\"vmDJ3TaMN3q{*o,bCXkd\">@app-state</field>\n      </block>\n    </value>\n  </block>\n</xml>"
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"er1=:-Q@H4.`{z*LoO%k\">:div</variable>\n    <variable id=\"}r66VqN|SjB_s`C!7##,\">:p</variable>\n  </variables>\n  <block type=\"args-2\" id=\"+SUu5L4Wp|#B5,p7$b%U\" x=\"16\" y=\"18\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"3h5oe@%yh-Trq{G8]oDM\">\n        <field name=\"VAR\" id=\"er1=:-Q@H4.`{z*LoO%k\">:div</field>\n      </block>\n    </value>\n  </block>\n  <block type=\"args-2\" id=\"`ks0S:3s|r}`3#{n^lgo\" x=\"15\" y=\"79\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"@rFDzJShJ_6/pmHOviS6\">\n        <field name=\"VAR\" id=\"}r66VqN|SjB_s`C!7##,\">:p</field>\n      </block>\n    </value>\n    <value name=\"arg_2\">\n      <block type=\"text\" id=\"gBI#}|?X*;0m;I^ihQsC\">\n        <field name=\"dertext\">Hello, World!</field>\n      </block>\n    </value>\n  </block>\n</xml>"
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"er1=:-Q@H4.`{z*LoO%k\">:div</variable>\n    <variable id=\"i7xg^PY139NPqS|P+mS0\">:hr</variable>\n    <variable id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</variable>\n  </variables>\n  <block type=\"args-1\" id=\"j+9_mcyIqs(@Ab/x=tie\" x=\"22\" y=\"33\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"e^%b]5^#C[(J75C5Qk!A\">\n        <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n      </block>\n    </value>\n  </block>\n  <block type=\"args-1\" id=\"lYeKLoa8hiQH]OYy50_A\" x=\"136\" y=\"34\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\",2b|?@y`{a.4`n#@^q|~\">\n        <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n      </block>\n    </value>\n  </block>\n  <block type=\"variables_get\" id=\"=5LhR2qV1[xSu=Aq5v$#\" x=\"90\" y=\"98\">\n    <field name=\"VAR\" id=\"er1=:-Q@H4.`{z*LoO%k\">:div</field>\n  </block>\n  <block type=\"args-4\" id=\"@?/U]zSW_-DZ1l6.F{xN\" inline=\"false\" x=\"21\" y=\"102\">\n    <value name=\"arg_3\">\n      <block type=\"args-2\" id=\"?}NF3f*CAvT|9dP7!4f%\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"Oixrfg=hgN)i_FXcs9hm\">\n            <field name=\"VAR\" id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"text\" id=\"I5{3I|l4_0+,1V6o(nsS\">\n            <field name=\"dertext\">Being in the World</field>\n          </block>\n        </value>\n      </block>\n    </value>\n  </block>\n</xml>"
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"er1=:-Q@H4.`{z*LoO%k\">:div</variable>\n    <variable id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</variable>\n    <variable id=\"i7xg^PY139NPqS|P+mS0\">:hr</variable>\n    <variable id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</variable>\n  </variables>\n  <block type=\"args-2\" id=\"C^_1xyWim#gE0uK$XwbN\" x=\"64\" y=\"30\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"MhPQjPwwym!ha-2fQ@`.\">\n        <field name=\"VAR\" id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</field>\n      </block>\n    </value>\n  </block>\n  <block type=\"args-5\" id=\"j?1On]LhcKXbSRE}UR@A\" inline=\"false\" x=\"65\" y=\"108\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"=5LhR2qV1[xSu=Aq5v$#\">\n        <field name=\"VAR\" id=\"er1=:-Q@H4.`{z*LoO%k\">:div</field>\n      </block>\n    </value>\n    <value name=\"arg_2\">\n      <block type=\"args-1\" id=\"lYeKLoa8hiQH]OYy50_A\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\",2b|?@y`{a.4`n#@^q|~\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_3\">\n      <block type=\"args-2\" id=\"?}NF3f*CAvT|9dP7!4f%\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"Oixrfg=hgN)i_FXcs9hm\">\n            <field name=\"VAR\" id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"text\" id=\"I5{3I|l4_0+,1V6o(nsS\">\n            <field name=\"dertext\">Being in the World</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_5\">\n      <block type=\"args-1\" id=\"j+9_mcyIqs(@Ab/x=tie\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"e^%b]5^#C[(J75C5Qk!A\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n  </block>\n  <block type=\"text\" id=\";v8..jsm;yG9|!BW[=QT\" x=\"215\" y=\"224\">\n    <field name=\"dertext\">Hello, World. I don't do much.</field>\n  </block>\n</xml>"
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"J:c(QQ7BH=KzBBx,C]zE\">:on-click</variable>\n    <variable id=\"er1=:-Q@H4.`{z*LoO%k\">:div</variable>\n    <variable id=\"i7xg^PY139NPqS|P+mS0\">:hr</variable>\n    <variable id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</variable>\n    <variable id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</variable>\n  </variables>\n  <block type=\"args-5\" id=\"j?1On]LhcKXbSRE}UR@A\" inline=\"false\" x=\"65\" y=\"108\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"=5LhR2qV1[xSu=Aq5v$#\">\n        <field name=\"VAR\" id=\"er1=:-Q@H4.`{z*LoO%k\">:div</field>\n      </block>\n    </value>\n    <value name=\"arg_2\">\n      <block type=\"args-1\" id=\"lYeKLoa8hiQH]OYy50_A\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\",2b|?@y`{a.4`n#@^q|~\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_3\">\n      <block type=\"args-2\" id=\"?}NF3f*CAvT|9dP7!4f%\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"Oixrfg=hgN)i_FXcs9hm\">\n            <field name=\"VAR\" id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"text\" id=\"I5{3I|l4_0+,1V6o(nsS\">\n            <field name=\"dertext\">Being in the World</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_4\">\n      <block type=\"args-3\" id=\"X=(VLfR;!XIQ(QWtzH97\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"CDSV-dIKo6!v1Zie*5gv\">\n            <field name=\"VAR\" id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</field>\n          </block>\n        </value>\n        <value name=\"arg_3\">\n          <block type=\"text\" id=\";v8..jsm;yG9|!BW[=QT\">\n            <field name=\"dertext\">Hello, World. I still don't do much.</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_5\">\n      <block type=\"args-1\" id=\"j+9_mcyIqs(@Ab/x=tie\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"e^%b]5^#C[(J75C5Qk!A\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n  </block>\n  <block type=\"num_sym\" id=\"?%q=.Fjw%T`)sVw,)!IE\" x=\"132\" y=\"305\">\n    <field name=\"nummer\">nil</field>\n  </block>\n  <block type=\"map-h-2\" id=\"$(M_DqEl$4t21XguFG],\" x=\"129\" y=\"354\">\n    <field name=\"key-1\" id=\"J:c(QQ7BH=KzBBx,C]zE\">:on-click</field>\n  </block>\n</xml>"
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"+zRuUpt06h9J,!/$AIxY\">click-function</variable>\n    <variable id=\"J?Y5zR]x~t.y-!G2_HJ%\">println</variable>\n    <variable id=\"er1=:-Q@H4.`{z*LoO%k\">:div</variable>\n    <variable id=\"b=Rk$DDmUBOd:0FGpQe:\">@app-state</variable>\n    <variable id=\"i7xg^PY139NPqS|P+mS0\">:hr</variable>\n    <variable id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</variable>\n    <variable id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</variable>\n    <variable id=\"J:c(QQ7BH=KzBBx,C]zE\">:on-click</variable>\n  </variables>\n  <block type=\"variables_get\" id=\"[IP0gGRr2t[PR!.5I-]d\" x=\"107\" y=\"-8\">\n    <field name=\"VAR\" id=\"+zRuUpt06h9J,!/$AIxY\">click-function</field>\n  </block>\n  <block type=\"variables_get\" id=\"j=|(pmdw`y8uS,/0IPl-\" x=\"250\" y=\"-8\">\n    <field name=\"VAR\" id=\"+zRuUpt06h9J,!/$AIxY\">click-function</field>\n  </block>\n  <block type=\"funs-v-4-defn\" id=\".^=^mr=:Sz3aX!k!rCe+\" x=\"9\" y=\"11\">\n    <field name=\"funsnamen\">defn</field>\n    <value name=\"args-3\">\n      <block type=\"num_sym\" id=\"%[bR!3%0O,G4IaEJS)yK\">\n        <field name=\"nummer\">[ ]</field>\n      </block>\n    </value>\n  </block>\n  <block type=\"funs-h-2-cus\" id=\"cMOcB2VLPQr~dLo[YVp~\" x=\"168\" y=\"37\">\n    <field name=\"kopf\" id=\"J?Y5zR]x~t.y-!G2_HJ%\">println</field>\n    <value name=\"args-2\">\n      <block type=\"variables_get\" id=\"X`Y}=:oZ^MCuiER073Ge\">\n        <field name=\"VAR\" id=\"b=Rk$DDmUBOd:0FGpQe:\">@app-state</field>\n      </block>\n    </value>\n  </block>\n  <block type=\"args-5\" id=\"j?1On]LhcKXbSRE}UR@A\" inline=\"false\" x=\"-2\" y=\"123\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"=5LhR2qV1[xSu=Aq5v$#\">\n        <field name=\"VAR\" id=\"er1=:-Q@H4.`{z*LoO%k\">:div</field>\n      </block>\n    </value>\n    <value name=\"arg_2\">\n      <block type=\"args-1\" id=\"lYeKLoa8hiQH]OYy50_A\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\",2b|?@y`{a.4`n#@^q|~\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_3\">\n      <block type=\"args-2\" id=\"?}NF3f*CAvT|9dP7!4f%\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"Oixrfg=hgN)i_FXcs9hm\">\n            <field name=\"VAR\" id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"text\" id=\"I5{3I|l4_0+,1V6o(nsS\">\n            <field name=\"dertext\">Being in the World</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_4\">\n      <block type=\"args-3\" id=\"X=(VLfR;!XIQ(QWtzH97\" inline=\"false\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"CDSV-dIKo6!v1Zie*5gv\">\n            <field name=\"VAR\" id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"map-h-2\" id=\"$(M_DqEl$4t21XguFG],\">\n            <field name=\"key-1\" id=\"J:c(QQ7BH=KzBBx,C]zE\">:on-click</field>\n          </block>\n        </value>\n        <value name=\"arg_3\">\n          <block type=\"text\" id=\";v8..jsm;yG9|!BW[=QT\">\n            <field name=\"dertext\">Hello, World. I print zero in the Output area</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_5\">\n      <block type=\"args-1\" id=\"j+9_mcyIqs(@Ab/x=tie\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"e^%b]5^#C[(J75C5Qk!A\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n  </block>\n</xml>"
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"bj7=4Yq?,4#Nv8!GH+lr\">app-state</variable>\n    <variable id=\"b=Rk$DDmUBOd:0FGpQe:\">@app-state</variable>\n    <variable id=\"hUEb^dt[R^{*WZ:_=]g:\">inc</variable>\n    <variable id=\"+zRuUpt06h9J,!/$AIxY\">click-function</variable>\n    <variable id=\"heo).RyO5K~rro_qF6ll\">do</variable>\n    <variable id=\"er1=:-Q@H4.`{z*LoO%k\">:div</variable>\n    <variable id=\"J?Y5zR]x~t.y-!G2_HJ%\">println</variable>\n    <variable id=\"qL=iH~oPrxBso.2KK^he\">swap!</variable>\n    <variable id=\"i7xg^PY139NPqS|P+mS0\">:hr</variable>\n    <variable id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</variable>\n    <variable id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</variable>\n    <variable id=\"J:c(QQ7BH=KzBBx,C]zE\">:on-click</variable>\n  </variables>\n  <block type=\"variables_get\" id=\"X`Y}=:oZ^MCuiER073Ge\" x=\"-4\" y=\"17\">\n    <field name=\"VAR\" id=\"b=Rk$DDmUBOd:0FGpQe:\">@app-state</field>\n  </block>\n  <block type=\"variables_get\" id=\"C,v;ah%dEvf[JYt$^^14\" x=\"132\" y=\"18\">\n    <field name=\"VAR\" id=\"bj7=4Yq?,4#Nv8!GH+lr\">app-state</field>\n  </block>\n  <block type=\"variables_get\" id=\"F,+Mj]/mbIS;2_tQI7Vo\" x=\"260\" y=\"20\">\n    <field name=\"VAR\" id=\"hUEb^dt[R^{*WZ:_=]g:\">inc</field>\n  </block>\n  <block type=\"funs-v-4-defn\" id=\".^=^mr=:Sz3aX!k!rCe+\" x=\"-7\" y=\"70\">\n    <field name=\"funsnamen\">defn</field>\n    <value name=\"args-2\">\n      <block type=\"variables_get\" id=\"[IP0gGRr2t[PR!.5I-]d\">\n        <field name=\"VAR\" id=\"+zRuUpt06h9J,!/$AIxY\">click-function</field>\n      </block>\n    </value>\n    <value name=\"args-3\">\n      <block type=\"num_sym\" id=\"%[bR!3%0O,G4IaEJS)yK\">\n        <field name=\"nummer\">[ ]</field>\n      </block>\n    </value>\n    <value name=\"args-4\">\n      <block type=\"funs-h-3-cus\" id=\"!EbUMVKFB_gtG?IG/Xhw\" inline=\"false\">\n        <field name=\"kopf\" id=\"heo).RyO5K~rro_qF6ll\">do</field>\n        <value name=\"args-2\">\n          <block type=\"funs-h-3-cus\" id=\"l0{wA2x])YUtma0I8AX@\">\n            <field name=\"kopf\" id=\"qL=iH~oPrxBso.2KK^he\">swap!</field>\n          </block>\n        </value>\n        <value name=\"args-3\">\n          <block type=\"funs-h-2-cus\" id=\"cMOcB2VLPQr~dLo[YVp~\">\n            <field name=\"kopf\" id=\"J?Y5zR]x~t.y-!G2_HJ%\">println</field>\n          </block>\n        </value>\n      </block>\n    </value>\n  </block>\n  <block type=\"args-5\" id=\"j?1On]LhcKXbSRE}UR@A\" inline=\"false\" x=\"-16\" y=\"212\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"=5LhR2qV1[xSu=Aq5v$#\">\n        <field name=\"VAR\" id=\"er1=:-Q@H4.`{z*LoO%k\">:div</field>\n      </block>\n    </value>\n    <value name=\"arg_2\">\n      <block type=\"args-1\" id=\"lYeKLoa8hiQH]OYy50_A\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\",2b|?@y`{a.4`n#@^q|~\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_3\">\n      <block type=\"args-2\" id=\"?}NF3f*CAvT|9dP7!4f%\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"Oixrfg=hgN)i_FXcs9hm\">\n            <field name=\"VAR\" id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"text\" id=\"I5{3I|l4_0+,1V6o(nsS\">\n            <field name=\"dertext\">Being in the World</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_4\">\n      <block type=\"args-3\" id=\"X=(VLfR;!XIQ(QWtzH97\" inline=\"false\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"CDSV-dIKo6!v1Zie*5gv\">\n            <field name=\"VAR\" id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"map-h-2\" id=\"$(M_DqEl$4t21XguFG],\">\n            <field name=\"key-1\" id=\"J:c(QQ7BH=KzBBx,C]zE\">:on-click</field>\n            <value name=\"val-2\">\n              <block type=\"variables_get\" id=\"j=|(pmdw`y8uS,/0IPl-\">\n                <field name=\"VAR\" id=\"+zRuUpt06h9J,!/$AIxY\">click-function</field>\n              </block>\n            </value>\n          </block>\n        </value>\n        <value name=\"arg_3\">\n          <block type=\"text\" id=\";v8..jsm;yG9|!BW[=QT\">\n            <field name=\"dertext\">Hello, World. I count!</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_5\">\n      <block type=\"args-1\" id=\"j+9_mcyIqs(@Ab/x=tie\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"e^%b]5^#C[(J75C5Qk!A\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n  </block>\n</xml>"
   "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n  <variables>\n    <variable id=\"[:Wf~GF5W^QeDCsH!_yv\">str</variable>\n    <variable id=\"b=Rk$DDmUBOd:0FGpQe:\">@app-state</variable>\n    <variable id=\"+zRuUpt06h9J,!/$AIxY\">click-function</variable>\n    <variable id=\"heo).RyO5K~rro_qF6ll\">do</variable>\n    <variable id=\"er1=:-Q@H4.`{z*LoO%k\">:div</variable>\n    <variable id=\"qL=iH~oPrxBso.2KK^he\">swap!</variable>\n    <variable id=\"J?Y5zR]x~t.y-!G2_HJ%\">println</variable>\n    <variable id=\"i7xg^PY139NPqS|P+mS0\">:hr</variable>\n    <variable id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</variable>\n    <variable id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</variable>\n    <variable id=\"J:c(QQ7BH=KzBBx,C]zE\">:on-click</variable>\n    <variable id=\"bj7=4Yq?,4#Nv8!GH+lr\">app-state</variable>\n    <variable id=\"hUEb^dt[R^{*WZ:_=]g:\">inc</variable>\n  </variables>\n  <block type=\"funs-v-4-defn\" id=\".^=^mr=:Sz3aX!k!rCe+\" x=\"-15\" y=\"-8\">\n    <field name=\"funsnamen\">defn</field>\n    <value name=\"args-2\">\n      <block type=\"variables_get\" id=\"[IP0gGRr2t[PR!.5I-]d\">\n        <field name=\"VAR\" id=\"+zRuUpt06h9J,!/$AIxY\">click-function</field>\n      </block>\n    </value>\n    <value name=\"args-3\">\n      <block type=\"num_sym\" id=\"%[bR!3%0O,G4IaEJS)yK\">\n        <field name=\"nummer\">[ ]</field>\n      </block>\n    </value>\n    <value name=\"args-4\">\n      <block type=\"funs-h-3-cus\" id=\"!EbUMVKFB_gtG?IG/Xhw\" inline=\"false\">\n        <field name=\"kopf\" id=\"heo).RyO5K~rro_qF6ll\">do</field>\n        <value name=\"args-2\">\n          <block type=\"funs-h-3-cus\" id=\"l0{wA2x])YUtma0I8AX@\">\n            <field name=\"kopf\" id=\"qL=iH~oPrxBso.2KK^he\">swap!</field>\n            <value name=\"args-2\">\n              <block type=\"variables_get\" id=\"C,v;ah%dEvf[JYt$^^14\">\n                <field name=\"VAR\" id=\"bj7=4Yq?,4#Nv8!GH+lr\">app-state</field>\n              </block>\n            </value>\n            <value name=\"args-3\">\n              <block type=\"variables_get\" id=\"F,+Mj]/mbIS;2_tQI7Vo\">\n                <field name=\"VAR\" id=\"hUEb^dt[R^{*WZ:_=]g:\">inc</field>\n              </block>\n            </value>\n          </block>\n        </value>\n        <value name=\"args-3\">\n          <block type=\"funs-h-2-cus\" id=\"cMOcB2VLPQr~dLo[YVp~\">\n            <field name=\"kopf\" id=\"J?Y5zR]x~t.y-!G2_HJ%\">println</field>\n            <value name=\"args-2\">\n              <block type=\"variables_get\" id=\"X`Y}=:oZ^MCuiER073Ge\">\n                <field name=\"VAR\" id=\"b=Rk$DDmUBOd:0FGpQe:\">@app-state</field>\n              </block>\n            </value>\n          </block>\n        </value>\n      </block>\n    </value>\n  </block>\n  <block type=\"funs-h-3-cus\" id=\")Q6RDY]aCY8(j@!JLgbT\" x=\"-17\" y=\"127\">\n    <field name=\"kopf\" id=\"[:Wf~GF5W^QeDCsH!_yv\">str</field>\n    <value name=\"args-2\">\n      <block type=\"text\" id=\"No*;T1^9~)w5e`3gzB=1\">\n        <field name=\"dertext\"/>\n      </block>\n    </value>\n  </block>\n  <block type=\"args-6\" id=\"{sUvyJd{XB*J]+)dY@Uf\" inline=\"false\" x=\"-17\" y=\"172\">\n    <value name=\"arg_1\">\n      <block type=\"variables_get\" id=\"=5LhR2qV1[xSu=Aq5v$#\">\n        <field name=\"VAR\" id=\"er1=:-Q@H4.`{z*LoO%k\">:div</field>\n      </block>\n    </value>\n    <value name=\"arg_2\">\n      <block type=\"args-1\" id=\"lYeKLoa8hiQH]OYy50_A\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\",2b|?@y`{a.4`n#@^q|~\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_3\">\n      <block type=\"args-2\" id=\"?}NF3f*CAvT|9dP7!4f%\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"Oixrfg=hgN)i_FXcs9hm\">\n            <field name=\"VAR\" id=\"m0dy[tB-A(|kl3/3`T@B\">:h1</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"text\" id=\"I5{3I|l4_0+,1V6o(nsS\">\n            <field name=\"dertext\">Being in the World</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_4\">\n      <block type=\"args-3\" id=\"X=(VLfR;!XIQ(QWtzH97\" inline=\"false\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"CDSV-dIKo6!v1Zie*5gv\">\n            <field name=\"VAR\" id=\"/eBJ_@nxTRRP|D!)BX.;\">:button</field>\n          </block>\n        </value>\n        <value name=\"arg_2\">\n          <block type=\"map-h-2\" id=\"$(M_DqEl$4t21XguFG],\">\n            <field name=\"key-1\" id=\"J:c(QQ7BH=KzBBx,C]zE\">:on-click</field>\n            <value name=\"val-2\">\n              <block type=\"variables_get\" id=\"j=|(pmdw`y8uS,/0IPl-\">\n                <field name=\"VAR\" id=\"+zRuUpt06h9J,!/$AIxY\">click-function</field>\n              </block>\n            </value>\n          </block>\n        </value>\n        <value name=\"arg_3\">\n          <block type=\"text\" id=\";v8..jsm;yG9|!BW[=QT\">\n            <field name=\"dertext\">Hello, World. I obviously count.</field>\n          </block>\n        </value>\n      </block>\n    </value>\n    <value name=\"arg_6\">\n      <block type=\"args-1\" id=\"j+9_mcyIqs(@Ab/x=tie\">\n        <value name=\"arg_1\">\n          <block type=\"variables_get\" id=\"e^%b]5^#C[(J75C5Qk!A\">\n            <field name=\"VAR\" id=\"i7xg^PY139NPqS|P+mS0\">:hr</field>\n          </block>\n        </value>\n      </block>\n    </value>\n  </block>\n  <block type=\"variables_get\" id=\"vAz/y:%r^W%?@x5;x_uv\" x=\"214\" y=\"373\">\n    <field name=\"VAR\" id=\"b=Rk$DDmUBOd:0FGpQe:\">@app-state</field>\n  </block>\n</xml>"


   ])