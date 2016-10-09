package com.googleauth

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.scribe.model.Token
class OauthCallBackController {
    def oauthService

    def index() { }

    @Secured(['permitAll'])
    def google() {
        //get google token
        Token googleAccessToken = (Token) session[oauthService.findSessionKeyForAccessToken('google')]

        println "googleAccessToken : " + googleAccessToken

        //get users data
        def googleResource = oauthService.getGoogleResource(googleAccessToken, grailsApplication.config.grails.google.api.url)
        def googleResponse = JSON.parse(googleResource?.getBody())

        Map data = [id    : googleResponse.id, email: googleResponse.email, name: googleResponse.name, given_name: googleResponse.given_name, family_name: googleResponse.family_name,
                    gender: googleResponse.gender, link: googleResponse.link]

        println "data : " + data

//Rest of the code
    }
}
