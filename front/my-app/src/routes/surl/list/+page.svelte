<script lang="ts">
	import type { components } from '$lib/backend/apiV1/schema';
	import rq from '$lib/rq/rq.svelte';
	let surls = $state<components['schemas']['SurlDto'][]>([]);
	async function getSurls() {
		const { data, error } = await rq.getClient().GET('/api/v1/surls');
		if (data) {
			surls = data.data.items;
		}
	}
	$effect(() => {
		getSurls();
	});
</script>

<h1>SURL 목록</h1>
<ul>
	{#each surls as surl (surl.id)}
		<li>
			{surl.id} : {surl.url}
			<br />
			{surl.body}
		</li>
	{/each}
</ul>
