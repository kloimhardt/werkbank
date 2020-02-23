var initblocks = function(Blockly) {
Blockly.Blocks["funs-v-3"]= {
    init: function() {this.jsonInit(
        {"type": "funs-v-3",
         "message0": "%1 %2 %3",
         "args0": [
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["!","!"],
                  ["fn", "fn"],
                  ["+", "+"],
                  ["-", "-"],
                  ["*", "*"],
                  ["/", "/"],
                  ["up", "up"],
                  ["lg/L-harmonic","lg/L-harmonic"],
                  ["lg/L-cental-polar","lg/L-central-polar"],
                  ["mn/multidimensional-minimize","mn/multidimensional-minimize"]]},
             {"type": "input_value",
              "name": "args-2"},
             {"type": "input_value",
              "name": "args-3"}],
         "inputsInline": false,
         "output": null,
         "colour": 140,
         "tooltip": "",
         "helpUrl": ""})}};

Blockly.Blocks["funs-v-4"]= {
    init: function() {this.jsonInit(
        {"type": "funs-v-4",
         "message0": "%1 %2 %3 %4",
         "args0": [
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["!","!"],
                  ["+","+"],
                  ["up", "up"]]},
             {"type": "input_value",
              "name": "args-2"},
             {"type": "input_value",
              "name": "args-3"},
             {"type": "input_value",
              "name": "args-4"}],
         "inputsInline": false,
         "output": null,
         "colour": 230,
         "tooltip": "",
         "helpUrl": ""})}};

Blockly.Blocks["funs-v-4-defn"]= {
    init: function() {this.jsonInit(
        {"type": "funs-v-4-defn",
         "message0": "%1 %2 %3 %4",
         "args0": [
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["defn", "defn"]]},
             {"type": "input_value",
              "name": "args-2"},
             {"type": "input_value",
              "name": "args-3"},
             {"type": "input_value",
              "name": "args-4"}],
         "inputsInline": false,
         "colour": 230,
         "tooltip": "",
         "helpUrl": ""})}};

Blockly.Blocks["funs-v-3-def"]= {
    init: function() {this.jsonInit(
        {
            "type": "funs-v-3-def",
            "message0": "%1 %2 %3",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "onedef",
                    "options": [
                        [
                            "def",
                            "def"
                        ]
                    ]
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                }
            ],
            "inputsInline": false,
            "colour": 140,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["funs-v-3-fn"]= {
    init: function() {this.jsonInit(
        {
            "type": "funs-v-3-def",
            "message0": "%1 %2 %3",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "onefn",
                    "options": [
                        [
                            "fn",
                            "fn"
                        ]
                    ]
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                }
            ],
            "inputsInline": false,
            "output": null,
            "colour": 140,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["idfuns-v-3"]= {
    init: function() {this.jsonInit(
        {
            "type": "idfuns-v-3",
            "message0": "%1 \" %2 \" %3",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "vegatex",
                    "options": [
                        [
                            "print-text",
                            "print-text"
                        ],
                        [
                            "vega-lite",
                            "vega-lite"
                        ],
                        [
                            "tex",
                            "tex"
                        ],
                        ["div", "div"],
                        [
                            "tex-matrix",
                            "tex-matrix"
                        ],
                        [
                            "mute-print",
                            "mute-print"
                        ],
                        [
                            "mute-vega",
                            "mute-vega"
                        ],
                        [
                            "mute-tex",
                            "mute-tex"
                        ],
                    ]
                },
                {
                    "type": "field_input",
                    "name": "id",
                    "text": "id"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                }
            ],
            "inputsInline": true,
            "colour": 270,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["idfuns-v-4"]= {
    init: function() {this.jsonInit(
        {
            "type": "idfuns-v-4",
            "message0": "%1 \" %2 \" %3 %4",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "vegatex",
                    "options": [
                        [
                            "vega",
                            "vega"
                        ],
                        [
                            "mute-vega",
                            "mute-vega"
                        ]
                    ]
                },
                {
                    "type": "field_input",
                    "name": "id",
                    "text": "id"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                },
                {
                    "type": "input_value",
                    "name": "args-4"
                }
            ],
            "inputsInline": false,
            "colour": 140,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["funs-h-2"]= {
    init: function() {this.jsonInit(
        {"type": "funs-h-2",
         "message0": "%1 %2",
         "args0": [
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["!","!"],
                  ["-","-"],
                  ["sin","sin"],
                  ["cos", "cos"],
                  ["atan", "atan"],
                  ["square", "square"],
                  ["sqrt", "sqrt"],
                  ["up", "up"],
                  ["D", "D"],
                  ["partial", "partial"],
                  ["lg/L-free-particle", "lg/L-free-particle"],
                  ["Lagrange-equations","Lagrange-equations"],
                  ["Lagrangian->energy","Lagrangian->energy"],
                  ["literal-function","literal-function"],
                  ["F->C","F->C"],
                  ["simplify","simplify"],
                  ["Gamma","Gamma"],
                  ["velocity","velocity"],
                  ["points-xy->plot","points-xy->plot"],
                  ["points-xz->plot","points-xz->plot"],
                  ["points-tz->plot","points-tz->plot"]]},
             {"type": "input_value",
              "name": "args-2"}],
         "inputsInline": true,
         "output": null,
         "colour": 270,
         "tooltip": "",
         "helpUrl": ""}
    )}};

Blockly.Blocks['sym_funs-h-2']= {
    init: function() {this.jsonInit(
        {
            "type": "sym_funs-h-2",
            "message0": "%1",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "symbs",
                    "options": [
                        ["!","!"],
                        ["sin", "sin"],
                        ["cos", "cos"],
                        ["square", "square"],
                        ["identity","identity"],
                        ["Math/PI", "Math/PI"],
                        ["lg/L-free-particle", "lg/L-free-particle"],
                        ["p->r","p->r"],
                        ["s->r","s->r"],
                        ["points-xy->plot","points-xy->plot"],
                        ["points-xz->plot","points-xz->plot"],
                        ["points-tz->plot","points-tz->plot"]
                    ]
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": "#A65C81",
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["funs-h-3"]= {
    init: function() {this.jsonInit(
        {"type": "funs-h-3",
         "message0": "%1 %2 %3",
         "args0": [
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["!","!"],
                  ["fn", "fn"],
                  ["+", "+"],
                  ["-", "-"],
                  ["*", "*"],
                  ["/", "/"],
                  ["compose","compose"],
                  ["nth", "nth"],
                  ["up", "up"],
                  ["lg/L-harmonic","lg/L-harmonic"],
                  ["lg/L-uniform-acceleration","lg/L-uniform-acceleration"],
                  ["lg/L-central-rectangular","lg/L-central-rectangular"],
                  ["lg/L-central-polar","lg/L-central-polar"],
                  ["cross-product","cross-product"],
                  ["mn/multidimensional-minimize","mn/multidimensional-minimize"]]},
             {"type": "input_value",
              "name": "args-2"},
             {"type": "input_value",
              "name": "args-3"}],
         "inputsInline": true,
         "output": null,
         "colour": 140,
         "tooltip": "",
         "helpUrl": ""}
    )}};

Blockly.Blocks["inli-h-3"]= {
    init: function() {this.jsonInit(
        {"type": "inli-h-3",
         "message0": "%1 %2 %3",
         "args0": [
             {"type": "input_value",
              "name": "args-2"},
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["+", "+"],
                  ["-", "-"],
                  ["*", "*"],
                  ["/", "/"]
              ]},
             {"type": "input_value",
              "name": "args-3"}],
         "inputsInline": true,
         "output": null,
         "colour": 140,
         "tooltip": "",
         "helpUrl": ""}
    )}};

Blockly.Blocks["funs-h-4"]= {
    init: function() {this.jsonInit(
        {"type": "funs-h-4",
         "message0": "%1 %2 %3 %4",
         "args0": [
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["!","!"],
                  ["*","*"],
                  ["up","up"],
                  ["->local","->local"],
                  ["minimize","minimize"]]},
             {"type": "input_value",
              "name": "args-2"},
             {"type": "input_value",
              "name": "args-3"},
             {"type": "input_value",
              "name": "args-4"}],
         "inputsInline": true,
         "output": null,
         "colour": 230,
         "tooltip": "",
         "helpUrl": ""}
    )}};

Blockly.Blocks["funs-h-5"]= {
    init: function() {this.jsonInit(
        {"type": "funs-h-5",
         "message0": "%1 %2 %3 %4 %5",
         "args0": [
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["!","!"],
                  ["*","*"],
                  ["Lagrangian-action", "Lagrangian-action"],
                  ["make-path-txyz","make-path-txyz"],
                  ["make-path-tz","make-path-tz"]]},
             {"type": "input_value",
              "name": "args-2"},
             {"type": "input_value",
              "name": "args-3"},
             {"type": "input_value",
              "name": "args-4"},
             {"type": "input_value",
              "name": "args-5"}],
         "inputsInline": true,
         "output": null,
         "colour": 360,
         "tooltip": "",
         "helpUrl": ""}
    )}};

Blockly.Blocks["funs-h-6"]= {
    init: function() {this.jsonInit(
        {"type": "funs-h-6",
         "message0": "%1 %2 %3 %4 %5 %6",
         "args0": [
             {"type": "field_dropdown",
              "name": "funsnamen",
              "options": [
                  ["!","!"],
                  ["*","*"],
                  ["lg/make-path", "lg/make-path"]]},
             {"type": "input_value",
              "name": "args-2"},
             {"type": "input_value",
              "name": "args-3"},
             {"type": "input_value",
              "name": "args-4"},
             {"type": "input_value",
              "name": "args-5"},
             {"type": "input_value",
              "name": "args-6"}],
         "inputsInline": true,
         "output": null,
         "colour": "#A65C81",
         "tooltip": "",
         "helpUrl": ""}
    )}};

Blockly.Blocks["funs-h-2-cus"]= {
    init: function() {this.jsonInit(
        {
            "type": "funs-h-2-cus",
            "message0": "%1 %2",
            "args0": [
                {
                    "type": "field_variable",
                    "name": "kopf",
                    "variable": "?"
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 270,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["funs-h-3-cus"]= {
    init: function() {this.jsonInit(
        {
            "type": "funs-h-3-cus",
            "message0": "%1 %2 %3",
            "args0": [
                {
                    "type": "field_variable",
                    "name": "kopf",
                    "variable": "?"
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 140,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["funs-h-4-cus"]= {
    init: function() {this.jsonInit(
        {
            "type": "funs-h-4-cus",
            "message0": "%1 %2 %3 %4",
            "args0": [
                {
                    "type": "field_variable",
                    "name": "kopf",
                    "variable": "?"
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                },
                {
                    "type": "input_value",
                    "name": "args-4"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 230,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["funs-h-5-cus"]= {
    init: function() {this.jsonInit(
        {
            "type": "funs-h-5-cus",
            "message0": "%1 %2 %3 %4 %5",
            "args0": [
                {
                    "type": "field_variable",
                    "name": "kopf",
                    "variable": "?"
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                },
                {
                    "type": "input_value",
                    "name": "args-4"
                },
                {
                    "type": "input_value",
                    "name": "args-5"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 360,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["funs-h-6-cus"]= {
    init: function() {this.jsonInit(
        {
            "type": "funs-h-6-cus",
            "message0": "%1 %2 %3 %4 %5 %6",
            "args0": [
                {
                    "type": "field_variable",
                    "name": "kopf",
                    "variable": "?"
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                },
                {
                    "type": "input_value",
                    "name": "args-4"
                },
                {
                    "type": "input_value",
                    "name": "args-5"
                },
                {
                    "type": "input_value",
                    "name": "args-6"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": "#A65C81",
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["funs-h-7-cus"]= {
    init: function() {this.jsonInit(
        {
            "type": "funs-h-7-cus",
            "message0": "%1 %2 %3 %4 %5 %6 %7",
            "args0": [
                {
                    "type": "field_variable",
                    "name": "kopf",
                    "variable": "?"
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                },
                {
                    "type": "input_value",
                    "name": "args-3"
                },
                {
                    "type": "input_value",
                    "name": "args-4"
                },
                {
                    "type": "input_value",
                    "name": "args-5"
                },
                {
                    "type": "input_value",
                    "name": "args-6"
                },
                {
                    "type": "input_value",
                    "name": "args-7"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 140,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks["list-h-2"]= {
    init: function() {this.jsonInit(
        {
            "type": "list-h-2",
            "message0": "%1 %2",
            "args0": [
                {
                    "type": "input_value",
                    "name": "args-1",
                },
                {
                    "type": "input_value",
                    "name": "args-2"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 270,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks['let']= {
    init: function() {this.jsonInit(
        {
            "type": "let",
            "message0": "%1 %2 %3",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "funsnamen",
                    "options": [
                        [
                            "let",
                            "let"
                        ]
                    ]
                },
                {
                    "type": "input_statement",
                    "name": "arg-1"
                },
                {
                    "type": "input_value",
                    "name": "arg-2"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 140,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks['pair']= {
    init: function() {this.jsonInit(
        {
            "type": "pair",
            "message0": "%1 %2",
            "args0": [
                {
                    "type": "input_value",
                    "name": "arg-1"
                },
                {
                    "type": "input_value",
                    "name": "arg-2"
                }
            ],
            "inputsInline": true,
            "previousStatement": null,
            "nextStatement": null,
            "colour": 140,
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks['map']= {
    init: function() {this.jsonInit(
        {
            "type": "map",
            "message0": "%1 %2",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "onlymap",
                    "options": [
                        [
                            "{ }",
                            "{ }"
                        ]
                    ]
                },
                {
                    "type": "input_statement",
                    "name": "map_data"
                }
            ],
            "output": null,
            "colour": 215, //140
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks['vector']= {
    init: function() {this.jsonInit(
        {
            "type": "vector",
            "message0": "%1 %2",
            "args0": [
                {
                    "type": "field_dropdown",
                    "name": "onlyvector",
                    "options": [
                        [
                            "[ ]",
                            "[ ]"
                        ]
                    ]
                },
                {
                    "type": "input_statement",
                    "name": "vec_data"
                }
            ],
            "output": null,
            "colour": 195, //140
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks['args-1']= {
    init: function() {this.jsonInit(
        {
            "type": "args-1",
            "message0": "%1",
            "args0": [
                {
                    "type": "input_value",
                    "name": "arg_1"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 270,
            "tooltip": "",
            "helpUrl": ""}
    )}};

Blockly.Blocks['args-2']= {
    init: function() {this.jsonInit(
        {
            "type": "args-2",
            "message0": "%1 %2",
            "args0": [
                {
                    "type": "input_value",
                    "name": "arg_1"
                },
                {
                    "type": "input_value",
                    "name": "arg_2"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 140,
            "tooltip": "",
            "helpUrl": ""}
    )}};

Blockly.Blocks['args-3']= {
    init: function() {this.jsonInit(
        {
            "type": "args-3",
            "message0": "%1 %2 %3",
            "args0": [
                {
                    "type": "input_value",
                    "name": "arg_1"
                },
                {
                    "type": "input_value",
                    "name": "arg_2"
                },
                {
                    "type": "input_value",
                    "name": "arg_3"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 230,
            "tooltip": "",
            "helpUrl": ""}
    )}};

Blockly.Blocks['args-4']= {
    init: function() {this.jsonInit(
        {
            "type": "args-4",
            "message0": "%1 %2 %3 %4",
            "args0": [
                {
                    "type": "input_value",
                    "name": "arg_1"
                },
                {
                    "type": "input_value",
                    "name": "arg_2"
                },
                {
                    "type": "input_value",
                    "name": "arg_3"
                },
                {
                    "type": "input_value",
                    "name": "arg_4"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": 360,
            "tooltip": "",
            "helpUrl": ""}
    )}};

Blockly.Blocks['args-5']= {
    init: function() {this.jsonInit(
        {
            "type": "args-5",
            "message0": "%1 %2 %3 %4 %5",
            "args0": [
                {
                    "type": "input_value",
                    "name": "arg_1"
                },
                {
                    "type": "input_value",
                    "name": "arg_2"
                },
                {
                    "type": "input_value",
                    "name": "arg_3"
                },
                {
                    "type": "input_value",
                    "name": "arg_4"
                },
                {
                    "type": "input_value",
                    "name": "arg_5"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": "#A65C81",
            "tooltip": "",
            "helpUrl": ""}
    )}};

Blockly.Blocks['args-6']= {
    init: function() {this.jsonInit(
        {
            "type": "args-6",
            "message0": "%1 %2 %3 %4 %5 %6",
            "args0": [
                {
                    "type": "input_value",
                    "name": "arg_1"
                },
                {
                    "type": "input_value",
                    "name": "arg_2"
                },
                {
                    "type": "input_value",
                    "name": "arg_3"
                },
                {
                    "type": "input_value",
                    "name": "arg_4"
                },
                {
                    "type": "input_value",
                    "name": "arg_5"
                },
                {
                    "type": "input_value",
                    "name": "arg_6"
                }
            ],
            "inputsInline": true,
            "output": null,
            "colour": "140",
            "tooltip": "",
            "helpUrl": ""}
    )}};

Blockly.Blocks['num']= {
    init: function() {this.jsonInit(
        {
            "type": "num",
            "message0": "%1",
            "args0": [
                {
                    "type": "field_input",
                    "name": "nummer",
                    "text": "0.0"
                }
            ],
            "output": null,
            "colour": "#A65C81",
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks['num_sym']= {
    init: function() {this.jsonInit(
        {
            "type": "num_sym",
            "message0": "%1",
            "args0": [
                {
                    "type": "field_input",
                    "name": "nummer",
                    "text": "\'m"
                }
            ],
            "output": null,
            "colour": "#A65C81",
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

Blockly.Blocks['text']= {
    init: function() {this.jsonInit(
        {
            "type": "text",
            "message0": "\" %1 \"",
            "args0": [
                {
                    "type": "field_input",
                    "name": "dertext",
                    "text": ""
                }
            ],
            "output": null,
            "colour": "#A65C81",
            "tooltip": "",
            "helpUrl": ""
        }
    )}};

    Blockly.Blocks["map-h-2"]= {
        init: function() {this.jsonInit(
            {
                "type": "map-h-2",
                "message0": "%1 %2",
                "args0": [
                    {
                        "type": "field_variable",
                        "name": "key-1",
                        "variable": ":"
                    },
                    {
                        "type": "input_value",
                        "name": "val-2"
                    }
                ],
                "inputsInline": true,
                "output": null,
                "colour": 270,
                "tooltip": "",
                "helpUrl": ""
            }
        );}};

    Blockly.Blocks["map-h-4"]= {
        init: function() {this.jsonInit(
            {
                "type": "map-h-4",
                "message0": "%1 %2 %3 %4",
                "args0": [
                    {
                        "type": "field_variable",
                        "name": "key-1",
                        "variable": ":"
                    },
                    {
                        "type": "input_value",
                        "name": "val-2"
                    },
                    {
                        "type": "field_variable",
                        "name": "key-3",
                        "variable": ":"
                    },
                    {
                        "type": "input_value",
                        "name": "val-4"
                    }
                ],
                "inputsInline": true,
                "output": null,
                "colour": 140,
                "tooltip": "",
                "helpUrl": ""
            }
        );}};

};
