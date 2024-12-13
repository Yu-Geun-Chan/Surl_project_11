<script lang="ts">
	import rq from '$lib/rq/rq.svelte';

	async function logout() {
		await rq.getClient().DELETE('/api/v1/members/logout');
		rq.goto('/');
	}
</script>

<header>
	<nav>
		<a href="/">메인</a>
		<a href="/member/login">로그인</a>
		<button type="button" on:click|preventDefault={logout}>로그아웃</button>
		<a href="/member/me">내 정보</a>
	</nav>
</header>

<slot></slot>
