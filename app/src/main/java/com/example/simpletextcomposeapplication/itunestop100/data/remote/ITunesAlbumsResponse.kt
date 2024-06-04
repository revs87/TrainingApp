package com.example.simpletextcomposeapplication.itunestop100.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ITunesAlbumsResponse(
    @SerialName("feed")
    val feed: Feed
)

@Serializable
data class Feed(
    @SerialName("author")
    val author: Author,
    @SerialName("entry")
    val entry: List<Entry>,
    @SerialName("icon")
    val icon: Icon,
    @SerialName("id")
    val id: IdX,
    @SerialName("link")
    val link: List<LinkX>,
    @SerialName("rights")
    val rights: RightsX,
    @SerialName("title")
    val title: TitleX,
    @SerialName("updated")
    val updated: Updated
)

@Serializable
data class Author(
    @SerialName("name")
    val name: Name,
    @SerialName("uri")
    val uri: Uri
)

@Serializable
data class Entry(
    @SerialName("category")
    val category: Category,
    @SerialName("id")
    val id: Id,
    @SerialName("im:artist")
    val imArtist: ImArtist,
    @SerialName("im:contentType")
    val imContentType: ImContentType,
    @SerialName("im:image")
    val imImage: List<ImImage>,
    @SerialName("im:itemCount")
    val imItemCount: ImItemCount,
    @SerialName("im:name")
    val imName: ImName,
    @SerialName("im:price")
    val imPrice: ImPrice,
    @SerialName("im:releaseDate")
    val imReleaseDate: ImReleaseDate,
    @SerialName("link")
    val link: Link,
    @SerialName("rights")
    val rights: RightsX,
    @SerialName("title")
    val title: Title
)

@Serializable
data class Title(
    @SerialName("label")
    val label: String
)

@Serializable
data class Icon(
    @SerialName("label")
    val label: String
)

@Serializable
data class IdX(
    @SerialName("label")
    val label: String
)

@Serializable
data class LinkX(
    @SerialName("attributes")
    val attributes: AttributesXXXXXXXXX
)

@Serializable
data class RightsX(
    @SerialName("label")
    val label: String
)

@Serializable
data class TitleX(
    @SerialName("label")
    val label: String
)

@Serializable
data class Updated(
    @SerialName("label")
    val label: String
)

@Serializable
data class Name(
    @SerialName("label")
    val label: String
)

@Serializable
data class Uri(
    @SerialName("label")
    val label: String
)

@Serializable
data class Category(
    @SerialName("attributes")
    val attributes: Attributes
)

@Serializable
data class Id(
    @SerialName("attributes")
    val attributes: AttributesX,
    @SerialName("label")
    val label: String
)

@Serializable
data class ImArtist(
    @SerialName("attributes")
    val attributes: AttributesXX?,
    @SerialName("label")
    val label: String
)

@Serializable
data class ImContentType(
    @SerialName("attributes")
    val attributes: AttributesXXX,
    @SerialName("im:contentType")
    val imContentType: ImContentTypeX
)

@Serializable
data class ImImage(
    @SerialName("attributes")
    val attributes: AttributesXXXXX,
    @SerialName("label")
    val label: String
)

@Serializable
data class ImItemCount(
    @SerialName("label")
    val label: String
)

@Serializable
data class ImName(
    @SerialName("label")
    val label: String
)

@Serializable
data class ImPrice(
    @SerialName("attributes")
    val attributes: AttributesXXXXXX,
    @SerialName("label")
    val label: String
)

@Serializable
data class ImReleaseDate(
    @SerialName("attributes")
    val attributes: AttributesXXXXXXX,
    @SerialName("label")
    val label: String
)

@Serializable
data class Link(
    @SerialName("attributes")
    val attributes: AttributesXXXXXXXX
)

@Serializable
data class Attributes(
    @SerialName("im:id")
    val imId: String,
    @SerialName("label")
    val label: String,
    @SerialName("scheme")
    val scheme: String,
    @SerialName("term")
    val term: String
)

@Serializable
data class AttributesX(
    @SerialName("im:id")
    val imId: String
)

@Serializable
data class AttributesXX(
    @SerialName("href")
    val href: String
)

@Serializable
data class AttributesXXX(
    @SerialName("label")
    val label: String,
    @SerialName("term")
    val term: String
)

@Serializable
data class ImContentTypeX(
    @SerialName("attributes")
    val attributes: AttributesXXX
)

@Serializable
data class AttributesXXXXX(
    @SerialName("height")
    val height: String
)

@Serializable
data class AttributesXXXXXX(
    @SerialName("amount")
    val amount: String,
    @SerialName("currency")
    val currency: String
)

@Serializable
data class AttributesXXXXXXX(
    @SerialName("label")
    val label: String
)

@Serializable
data class AttributesXXXXXXXX(
    @SerialName("href")
    val href: String,
    @SerialName("rel")
    val rel: String,
    @SerialName("type")
    val type: String
)

@Serializable
data class AttributesXXXXXXXXX(
    @SerialName("href")
    val href: String,
    @SerialName("rel")
    val rel: String,
    @SerialName("type")
    val type: String?
)