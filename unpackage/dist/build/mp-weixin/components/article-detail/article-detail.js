(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["components/article-detail/article-detail"],{"0251":function(t,e,o){"use strict";o.r(e);var i=o("238a"),n=o("2f1f");for(var s in n)"default"!==s&&function(t){o.d(e,t,(function(){return n[t]}))}(s);o("8716");var a,u=o("f0c5"),c=Object(u["a"])(n["default"],i["b"],i["c"],!1,null,null,null,!1,i["a"],a);e["default"]=c.exports},1441:function(t,e,o){"use strict";(function(t){Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var i=o("5ec6"),n=o("2f62");function s(t,e){var o=Object.keys(t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(t);e&&(i=i.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),o.push.apply(o,i)}return o}function a(t){for(var e=1;e<arguments.length;e++){var o=null!=arguments[e]?arguments[e]:{};e%2?s(Object(o),!0).forEach((function(e){u(t,e,o[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(o)):s(Object(o)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(o,e))}))}return t}function u(t,e,o){return e in t?Object.defineProperty(t,e,{value:o,enumerable:!0,configurable:!0,writable:!0}):t[e]=o,t}var c=function(){Promise.all([o.e("common/vendor"),o.e("components/gaoyia-parse/parse")]).then(function(){return resolve(o("fe92"))}.bind(null,o)).catch(o.oe)},r=function(){o.e("components/comments-box/comments-box").then(function(){return resolve(o("ff83"))}.bind(null,o)).catch(o.oe)},l=function(){o.e("components/action-bottom/action-bottom").then(function(){return resolve(o("3212"))}.bind(null,o)).catch(o.oe)},h={components:{UParse:c,CommentBox:r,ActionBottom:l},computed:a({},(0,n.mapState)(["userid","systemUserInfo"])),filters:{formatTime:function(t){return(0,i.convertDate)(t)}},props:{article:{type:Object,default:function(){return{}}}},watch:{article:function(t){var e=t;this.article=e,this.title=e.title,this.author_name=e.author_name,this.browse_count=e.browse_count,this.thumbs_up_count=e.thumbs_up_count,this.create_time=e.create_time,this.is_like=e.is_like,this.is_thumbs_up=e.is_thumbs_up,this.is_author_like=e.is_author_like,this.getContent(),this.getComment()},content:function(t){this.content=t},commentList:function(t){this.commentList=t}},mounted:function(){var t=this.article;this.title=t.title,this.author_name=t.author_name,this.browse_count=t.browse_count,this.thumbs_up_count=t.thumbs_up_count,this.create_time=t.create_time,this.is_like=t.is_like,this.is_thumbs_up=t.is_thumbs_up,this.is_author_like=t.is_author_like,this.getContent(),this.getComment()},data:function(){return{noData:'<p style="text-align:center; color=#666">文章加载中...</p>',commentValue:"",commentList:[],updateCommentData:{},content:"",title:"Loading",author_name:"Loading",browse_count:0,thumbs_up_count:0,create_time:"Loading",is_author_like:!1,is_like:!1,is_thumbs_up:!1}},methods:{getContent:function(){var e=this,o=this.article.content;"normal"===this.article.status&&t.request({url:o,success:function(t){var o=t.data;e.content=o},fail:function(t){}})},follow:function(t){var e=this.userid;null===e?(0,i.showLoginModel)():(this.is_author_like=!this.is_author_like,this.updateAuthorLike(e,t))},updateAuthorLike:function(e,o){var i=this;t.showLoading(),t.request({url:"http://localhost:8000/forum/author_like",data:{id:e,author:o},success:function(e){if(200===e.data.code){var o=i.systemUserInfo;t.hideLoading(),t.$emit("update_author_follow"),t.showToast({title:i.is_author_like?"关注作者成功":"取消关注"}),i.is_author_like?o.follow_count=o.follow_count+1:o.follow_count=o.follow_count-1,i.$store.dispatch("set_system_userinfo",o)}else t.hideLoading(),i.is_author_like=!i.is_author_like,t.showToast({title:"关注失败, 请稍后再试",icon:"none"})},fail:function(e){t.hideLoading(),i.is_author_like=!i.is_author_like,t.showToast({title:"关注失败, 请稍后再试",icon:"none"})}})},likeTap:function(t){var e=this.userid;null===e?(0,i.showLoginModel)():(this.is_like=!this.is_like,this.updateArticleLike(e,t))},updateArticleLike:function(e,o){var i=this;t.showLoading(),t.request({url:"http://localhost:8000/forum/update_like",data:{id:e,article:o},success:function(e){t.hideLoading(),200===e.data.code?(t.showToast({title:i.is_like?"收藏成功":"取消收藏",icon:i.is_like?"success":"none"}),t.$emit("update_article_like","follow")):(i.is_like=!i.is_like,t.showToast({title:"收藏失败, 请稍后再试",icon:"none"}))},fail:function(){t.hideLoading(),i.like=!i.like,t.showToast({title:"收藏失败, 请稍后再试",icon:"none"})}})},thumbsup:function(e){var o=this.userid;null===o?(0,i.showLoginModel)():this.is_thumbs_up?t.showToast({title:"你已经点过赞了",icon:"none"}):(this.is_thumbs_up=!this.is_thumbs_up,this.updateArticleThumbs(o,e))},updateArticleThumbs:function(e,o){var i=this;t.showLoading(),t.request({url:"http://localhost:8000/forum/update_thumbsup",data:{id:e,article:o},success:function(e){200===e.data.code?(t.hideLoading(),i.thumbs_up_count++,t.showToast({title:e.data.data,icon:"none"})):(t.hideLoading(),i.is_thumbs_up=!i.is_thumbs_up,t.showToast({title:"点赞失败, 请稍后再试",icon:"none"}))},fail:function(e){t.hideLoading(),i.is_thumbs_up=!i.is_thumbs_up,t.showToast({title:"点赞失败, 请稍后再试",icon:"none"})}})},open:function(){var e=t.createSelectorQuery().in(this);e.select(".comment").boundingClientRect((function(e){t.pageScrollTo({duration:0,scrollTop:e.top})})).exec()},openCommentView:function(t){null!==this.userid?(0===t?this.updateCommentData={father_id:this.article._id,is_reply:0,tos:"",to_user_id:""}:t.is_reply?this.updateCommentData={father_id:t.comments.father_id,is_reply:1,tos:t.comments.author_name,to_user_id:t.comments.author_id}:this.updateCommentData={father_id:t.comments.comment_id,is_reply:1,tos:"",to_user_id:""},this.$refs.popup.open()):(0,i.showLoginModel)()},closeCommentView:function(){this.$refs.popup.close(),this.updateCommentData={}},getComment:function(){var e=this;t.request({url:"http://localhost:8000/forum/get_comment",data:{article:this.article._id},success:function(t){200===t.data.code&&(e.commentList=t.data.data)},fail:function(t){}})},reply:function(t){this.openCommentView(t)},publish:function(t){this.updateComment(a({data:t},this.updateCommentData))},updateComment:function(e){var o=this;t.showLoading(),t.request({method:"GET",url:"http://localhost:8000/forum/update_comment",data:{cid:"cm"+(0,i.getID)(5),content:e.data,reply:e.is_reply,father:e.father_id,aid:this.userid,name:this.systemUserInfo.author_name,avatar:this.systemUserInfo.avatar,to:e.tos,to_uid:e.to_user_id},success:function(e){200===e.data.code?(o.getComment(),t.hideLoading(),t.showToast({title:"评论成功"}),o.closeCommentView()):(t.hideLoading(),t.showToast({title:"评论失败, 请稍后再试",icon:"none"}),o.closeCommentView())},fail:function(e){t.hideLoading(),t.showToast({title:"评论失败, 请稍后再试",icon:"none"}),o.closeCommentView()}})}}};e.default=h}).call(this,o("543d")["default"])},1466:function(t,e,o){},"238a":function(t,e,o){"use strict";o.d(e,"b",(function(){return n})),o.d(e,"c",(function(){return s})),o.d(e,"a",(function(){return i}));var i={commentsBox:function(){return o.e("components/comments-box/comments-box").then(o.bind(null,"ff83"))},uniIcons:function(){return Promise.all([o.e("common/vendor"),o.e("components/uni-icons/uni-icons")]).then(o.bind(null,"25da"))},actionBottom:function(){return o.e("components/action-bottom/action-bottom").then(o.bind(null,"3212"))}},n=function(){var t=this,e=t.$createElement,o=(t._self._c,t._f("formatTime")(t.create_time));t.$mp.data=Object.assign({},{$root:{f0:o}})},s=[]},"2f1f":function(t,e,o){"use strict";o.r(e);var i=o("1441"),n=o.n(i);for(var s in i)"default"!==s&&function(t){o.d(e,t,(function(){return i[t]}))}(s);e["default"]=n.a},8716:function(t,e,o){"use strict";var i=o("1466"),n=o.n(i);n.a}}]);
;(global["webpackJsonp"] = global["webpackJsonp"] || []).push([
    'components/article-detail/article-detail-create-component',
    {
        'components/article-detail/article-detail-create-component':(function(module, exports, __webpack_require__){
            __webpack_require__('543d')['createComponent'](__webpack_require__("0251"))
        })
    },
    [['components/article-detail/article-detail-create-component']]
]);
