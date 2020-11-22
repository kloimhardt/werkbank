## Video

This [video](https://youtu.be/V7unwER5wFc) shows the purpose of Werkbank by demonstrating some features of the sophisticated [SICMutils](https://github.com/littleredcomputer/sicmutils) library. The video is meant for experts in the domain of theoretical Physics, but muting the sound and watching the tiles moving for one or two minutes gives a good impression.

## Graphical puzzle to learn the basics of Clojure on-line

 [clj-tiles](https://kloimhardt.github.io/clj_blocks.html) is a Clojure tutorial where you can try your hands on some blocks.

## Note on state of repository (Nov 2020)

I developed Werkbank in 2019. Since then, [Oz](https://github.com/metasoarous/oz) and [Saite](https://github.com/jsa-aerial/saite) made huge progress and [Notespace](https://github.com/scicloj/notespace) and [Blockoid](https://github.com/ParkerICI/blockoid) appeared. Werkbank, instead of living in this ecosystem, uses custom code. Moreover, the code for the clj-tiles tutorial is not in the repository, it can be accessed via Chrome's devtools (in Sources/.../block-cljs/). 

The video and tutorial, however, are still close to the best I could come up with. But here is a new opportunity: since SICMUtils has been ported to ClojureScript, an on-line graphical puzzle to learn SICMUtils has become feasible. The main effort would be to figure out suitable puzzles, which best required a feedback audience in the form of an actual physics seminar.

## Werkbank: Showing the power of Lisp without the parentheses

![screenshot](https://kloimhardt.github.io/werkbank_fullscreen.png)

In contrast to the above tutorial about the basics of Clojure, the fully-fledged werkbank project does not work on-line. You have to install [Clojure](https://www.clojure.org/guides/getting_started).

Only remarkebly few building-blocks are needed to use the full library:

![screenshotmenus](https://kloimhardt.github.io/werkbank_menus.png)

To be sure (as any Clojurist will quickly see), the number of building-blocks could be reduced further without restricting access to the library. But the point of this project is to present the power of the language and thus to choose instructive building-blocks, not a minimal set.

The project runs (and is meant to run) locally in dev mode with figwheel. On pressing the "run" button, a file containing Clojure code is generated and executed, just as if it were typed and saved using a keyboard. So there is no restriction to any specific library, anything can be added to deps.edn and used like in a normal project. At a certain point, a programmer even can forget about Blockly and continue hacking the code in one's favourite editor.

Blockly is very well suited for ready made demonstrations to experts outside of programming, e.g. experts in physics or graphical art or cancer research. Namely to show them how their very own domian can be fruitfully modelled by programmers using Clojure Libraries. Here I demonstarte the use of [SICMutils](https://github.com/littleredcomputer/sicmutils) and (further down) [La Habra](https://github.com/sarahgp/la-habra), but datalog/datahike comes to mind. Here, in this figwheel-driven project, I do not see Blockly so much as a tool to teach programming in general, imho for this the text-based approach of Racket and Maria-Cloud is best. However, the above [on-line example](https://kloimhardt.github.io/clj_blocks.html) is my take on this issue of providing a Clojure beginner experience. In any case, Blockly (hiding parens!) should wet the appetite for problem solving in Clojure. The two way grahical+text approach of Python based BlockPy also shows a way in that direction.

The Video demonstration is based on the [SICM book by Sussman  et.al.](https://mitpress.mit.edu/sites/default/files/titles/content/sicm_edition_2/book.html). It shows how to calculate a beast known as Lagrangian Equation using the [SICMUtils Library](https://github.com/littleredcomputer/sicmutils). In addition, a more comprehensive Blockly Workspace for SICMUtils, which also shows the plotting capabilities of the [Vega Library](https://vega.github.io/vega), is provided (see below). 

The second (short) video further down demonstartes the use of the [La Habra Library](https://github.com/sarahgp/la-habra), which was shown (using a keyboard) at the 2019 [Heart of Clojure](https://www.youtube.com/watch?v=F4pozY_RF5c) conference.

## Building the project

to build, enter the following shell command:

    ./startcljblocks.sh

to start the server, enter the following url in browser (ignore "Adress already in use" error messages if any):

localhost:9500/http-kit

## SICMUtils Example

to run a workspace based on the library littleredcomputer/sicmutils, enter the following url in browser:

localhost:3000/abc.html

to open the Sicmutils plotting area:

localhost:3000/vegatex

to open a sample workspace press the "Choose file" button and open "sample_workspaces/workspace_full.xml"

There is also a draft for a paper (in German with English abstract) "ga_pro_kla_mech.pdf" in the "/latex" directory

After the initial fun with Blocks, you may think that modelling via text is faster. In that case you can switch to the according [Jupyter Notebook](https://github.com/kloimhardt/sicmutils/blob/master/jupyter/book-examples-lab.ipynb) or...

Start text-based modelling in SICMUtils without any local installation and remix the according [Nextjournal Notebook](https://nextjournal.com/SICM_in_Clojure/structure-and-interpretation-of-classical-mechanics-in-clojure).

## La Habra Example

To run a workspace based on the library sarahgp/la-habra, open "sample_workspaces/workspace_habra.xml" and open the graphics area:

localhost:3000/habra

demo video:

https://kloimhardt.github.io/la_habra_blocks.mp4

(If errors occur during la-habra live reloading, it helps to open dev.cljs.edn and increase :wait-time-ms from  999 to 5000 milliseconds.)

## Adding your own library (requires basic Clojure experience)

To add a library, you need to modify the deps.edn file and start the whole project anew as shown above. Within the Blockly Workspace, you can do anything that is possible in a normal Clojure source file. That means that adding your own libraries is just not possible without this restart.

In a next step, you may want to modify the (:require ...) expression in the file code_template/code_trunk.clj. During the run process, this file is concatenated with the Blockly-represented code and gives the final code file src/abc/code.clj. It in turn is compiled and executed via the developer tool figwheel.

I do very much see the need for a Clojure beginner environment, but I fear this is not a fully fledged one. The Blockly Workspace is a substitute for a text editor, it creates a Clojure source file on disk, just as if it were typed and saved with a normal text editor. I think the environment is extremely suitable to show how to model some domain using the concise syntax of Clojure. The advatage over the textual representation is that in Blockly there are no parantheses. And they seem to shy away people.

## Syntax vs. Code is Data

The following two function definitions look different on the workspace but result in exactly the same Clojure code:

![screenshot code_is_data](https://kloimhardt.github.io/code_is_data.png)

To be sure, only the left version sould be used, I think the right one is confusing. But this example shows, that one could in priciple omit the whole building-block section named "defn" (which is visible in the screenshot farther above).

Such an omission, while cursed, is made possible by this certain Lisp super-power which is often termed with slogans as "Lisp has no Syntax", "Homoiconicity" or "Code is Data". I think this super-power should not be mentioned when Clojure is presentd or described. It is better to show the normal power of Clojure, namely its lean Syntax for accessing powerful libraries.

So, the chosen building blocks move away from the code-is-data paradigm. Because they allow to represent the same edn-vector in two different graphical ways. This means that in the workspace examples a choice is made, a choice for a certain (and hopefully gentle) Syntax to represent Clojure-code.

In the same vein, infix notation for adding (multiplication etc.) of numbers is introduced. Maybe it is best to explain infix notation like shown below:

![screenshot infix](https://kloimhardt.github.io/infix.png)
