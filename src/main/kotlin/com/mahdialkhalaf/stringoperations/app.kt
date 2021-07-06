package com.mahdialkhalaf.stringoperations

import com.github.thomasnield.rxkotlinfx.toObservable
import javafx.application.Application
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import tornadofx.*

fun main(args: Array<String>) {
    Application.launch(StringOperationsApp::class.java, *args)
}

class StringOperationsApp : App(StringOperationsView::class)

class StringOperationsView : View("String Operations") {
    var area1: TextArea by singleAssign()
    var area2: TextArea by singleAssign()
    var count1: Label by singleAssign()
    var countNonBlank1: Label by singleAssign()
    var count2: Label by singleAssign()
    var countNonBlank2: Label by singleAssign()

    override val root = scrollpane {
        hbox {
            form {
                fieldset("Input") {
                    field {
                        area1 = textarea { }
                    }
                    field("# of lines") {
                        count1 = label("")

                        area1.textProperty().toObservable().subscribe {
                            count1.text = it.lines().size.toString()
                        }
                    }
                    field("# of non-blank lines") {
                        countNonBlank1 = label("")

                        area1.textProperty().toObservable().subscribe {
                            countNonBlank1.text = it.lines().filter { it.isNotBlank() }.size.toString()
                        }
                    }
                }

                fieldset("Output") {
                    field {
                        area2 = textarea { }
                    }
                    field {
                        label("# of lines")
                        count2 = label("")

                        area2.textProperty().toObservable().subscribe {
                            count2.text = it.lines().size.toString()
                        }
                    }
                    field("# of non-blank lines") {
                        countNonBlank2 = label("")

                        area2.textProperty().toObservable().subscribe {
                            countNonBlank2.text = it.lines().filter { it.isNotBlank() }.size.toString()
                        }
                    }
                }
            }

            form {
                fieldset("Operations") {
                    field("Split") {
                        label("Regex")
                        val text = textfield { }
                        button("Split").action {
                            area2.text = area1.text.split(Regex(text.text)).joinToString("\n")
                        }
                    }

                    field("Join") {
                        label("Separator")
                        val text = textfield { }
                        button("Join").action {
                            area2.text = area1.text.lines().joinToString(text.text)
                        }
                    }

                    field {
                        button("Remove Duplicate Lines").action {
                            area2.text = area1.text.lines()
                                    .asSequence()
                                    .map { it.trim() }
                                    .toSet()
                                    .joinToString("\n")
                        }
                    }

                    field {
                        button("Copy Output to Input").action {
                            area1.text = area2.text
                        }
                    }
                }
            }
        }
    }
}