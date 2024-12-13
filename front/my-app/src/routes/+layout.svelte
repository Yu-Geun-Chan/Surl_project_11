<script lang="ts">
	import rq from '$lib/rq/rq.svelte';

	async function logout() {
		await rq.getClient().DELETE('/api/v1/members/logout');
		rq.setLogout();
		rq.goto('/');
	}
	$effect(() => {
		rq.initAuth();
	});
</script>

<header>
	<nav>
		<a href="/">메인</a>
		{#if rq.isLogin()}
			<button type="button" on:click|preventDefault={logout}>로그아웃</button>
			<a href="/member/me">내 정보</a>
		{/if}
		{#if rq.isLogout()}
			<a href="/member/login">로그인</a>
		{/if}
	</nav>
</header>

<slot></slot>
