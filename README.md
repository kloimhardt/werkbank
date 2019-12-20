# werkbank

Translate visual Blockly tiles into executable Clojure code. Show the power of Lisp without parentheses.

![screenshot](https://kloimhardt.github.io/werkbank_fullscreen.png)

Look at the [demo video](https://kloimhardt.github.io/driven_pendulum.mp4) which shows the sophisticated [SICMutils](https://github.com/littleredcomputer/sicmutils) library in action. The video is meant for experts in the domain of theoretical Physics, but muting the sound and watching the tiles moving for one or two minutes gives a good impression. Only remarkebly few building-blocks are needed to use the full library:

![screenshotmenus](https://kloimhardt.github.io/werkbank_menus.png)

To be sure (as any Clojurist will quickly see), the number of building-blocks could be reduced further without restricting access to the library. But the point of this project is to present the power of the language and thus to choose instructive building-blocks, not a minimal set.

The project runs (and is meant to run) locally in dev mode with figwheel. On pressing the "run" button, a file containing Clojure code is generated and executed, just as if it were typed and saved using a keyboard. So there is no restriction to any specific library, anything can be added to deps.edn and used like in a normal project. At a certain point, a programmer even can forget about Blockly and continue hacking the code in one's favourite editor.

Blockly is very well suited for ready made demonstrations to experts outside of programming, e.g. experts in physics or graphical art or cancer research. Namely to show them how their very own domian can be fruitfully modelled by programmers using Clojure Libraries. Here I demonstarte the use of [SICMutils](https://github.com/littleredcomputer/sicmutils) and (further down) [La Habra](https://github.com/sarahgp/la-habra), but datalog/datahike comes to mind. Here, I do not see Blockly so much as a tool to teach programming in general, imho for this the text-based approach of Racket and Maria-Cloud is best. Blockly (hiding parens!) should merely wet the appetite for problem solving in Clojure. The two way grahical+text approach of Python based BlockPy also shows a way in that direction. In this sense "Scratch is for kids" can be read as: they are experts in playing, let's wet their appetite to play in a meaningful way using computers.

The Video demonstration is based on the [SICM book by Sussman  et.al.](https://mitpress.mit.edu/sites/default/files/titles/content/sicm_edition_2/book.html). It shows how to calculate a beast known as Lagrangian Equation using the [SICMUtils Library](https://github.com/littleredcomputer/sicmutils). In addition, a more comprehensive Blockly Workspace for SICMUtils, which also shows the plotting capabilities of the [Vega Library](https://vega.github.io/vega), is provided (see below). 

The second (short) video further down demonstartes the use of the [La Habra Library](https://github.com/sarahgp/la-habra), which was shown (using a keyboard) at the 2019 [Herat of Clojure](https://www.youtube.com/watch?v=F4pozY_RF5c) conference.
## Showing the power of Lisp without the parentheses


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
