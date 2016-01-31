/* Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package geb.content

import geb.*
import geb.error.RequiredPageContentNotPresent
import geb.navigator.Navigator
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

@SuppressWarnings("FieldName")
abstract class TemplateDerivedPageContent implements Navigator {

    private PageContentTemplate _template
    private Object[] _args
    private Browser _browser

    @Delegate
    protected Navigator _navigator

    /**
     * Called by the template when created (i.e. is not public).
     *
     * We don't use a constructor to prevent users from having to implement them.
     */
    void init(Browser browser, PageContentTemplate template, Navigator navigator, Object[] args) {
        this._browser = browser
        this._template = template
        this._navigator = navigator
        this._args = args
    }

    String toString() {
        "${_template.name} - ${this.class.simpleName} (owner: ${_template.owner}, args: $_args, value: ${_navigator.value()})"
    }

    Browser getBrowser() {
        _browser
    }

    WebDriver getDriver() {
        getBrowser().driver
    }

    boolean isPresent() {
        !_navigator.empty
    }

    void require() {
        if (!isPresent()) {
            throw new RequiredPageContentNotPresent(this)
        }
        this
    }

    Navigator click() {
        def to = templateParams.toSingle ?: templateParams.toList
        if (to) {
            def toWait = _template.config.getWaitForParam(templateParams.toWait)
            _navigator.click(to, toWait)
        } else {
            _navigator.click()
        }
    }

    PageContentTemplateParams getTemplateParams() {
        _template.params
    }

    boolean asBoolean() {
        _navigator.asBoolean()
    }

    def methodMissing(String name, args) {
        _navigator."$name"(*args)
    }

    def propertyMissing(String name) {
        _navigator[name]
    }

    def propertyMissing(String name, val) {
        _navigator[name] = val
    }

    @Override
    Navigator $(Map<String, Object> predicates) {
        _navigator.$(predicates)
    }

    @Override
    Navigator $(Map<String, Object> predicates, int index) {
        _navigator.$(predicates, index)
    }

    @Override
    Navigator $(Map<String, Object> predicates, Range<Integer> range) {
        _navigator.$(predicates, range)
    }

    @Override
    Navigator $(Map<String, Object> predicates, String selector) {
        _navigator.$(predicates, selector)
    }

    @Override
    Navigator $(Map<String, Object> predicates, String selector, int index) {
        _navigator.$(predicates, selector, index)
    }

    @Override
    Navigator $(Map<String, Object> predicates, String selector, Range<Integer> range) {
        _navigator.$(predicates, selector, range)
    }

    @Override
    Navigator $(String selector) {
        _navigator.$(selector)
    }

    @Override
    Navigator $(String selector, int index) {
        _navigator.$(selector, index)
    }

    @Override
    Navigator $(String selector, Range<Integer> range) {
        _navigator.$(selector, range)
    }

    @Override
    Navigator $(Map<String, Object> predicates, By bySelector) {
        _navigator.$(predicates, bySelector)
    }

    @Override
    Navigator $(Map<String, Object> predicates, By bySelector, int index) {
        _navigator.$(predicates, bySelector, index)
    }

    @Override
    Navigator $(Map<String, Object> predicates, By bySelector, Range<Integer> range) {
        _navigator.$(predicates, bySelector, range)
    }

    @Override
    Navigator $(By bySelector) {
        _navigator.$(bySelector)
    }

    @Override
    Navigator $(By bySelector, int index) {
        _navigator.$(bySelector, index)
    }

    @Override
    Navigator $(By bySelector, Range<Integer> range) {
        _navigator.$(bySelector, range)
    }

    @Override
    //necessary because @Delegate generates wrong method signature for this method
    <T extends Module> T module(Class<T> moduleClass) {
        _navigator.module(moduleClass)
    }

    @Override
    //necessary because @Delegate generates wrong method signature for this method
    <T extends Module> T module(T module) {
        _navigator.module(module)
    }

    @Override
    //necessary because @Delegate generates wrong method signature for this method
    <T extends Module> List<T> moduleList(Class<T> moduleClass) {
        _navigator.moduleList(moduleClass)
    }

    @Override
    //necessary because @Delegate generates wrong method signature for this method
    <T extends Module> List<T> moduleList(Closure<T> moduleFactory) {
        _navigator.moduleList(moduleFactory)
    }

    @Override
    boolean isFocused() {
        _navigator.focused
    }
}